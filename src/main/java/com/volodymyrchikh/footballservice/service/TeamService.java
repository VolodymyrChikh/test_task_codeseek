package com.volodymyrchikh.footballservice.service;

import com.querydsl.core.types.Predicate;
import com.volodymyrchikh.footballservice.dto.*;
import com.volodymyrchikh.footballservice.exception.PlayerNotFoundException;
import com.volodymyrchikh.footballservice.exception.TeamNotFoundException;
import com.volodymyrchikh.footballservice.mapper.PlayerMapper;
import com.volodymyrchikh.footballservice.mapper.TeamMapper;
import com.volodymyrchikh.footballservice.model.Player;
import com.volodymyrchikh.footballservice.model.Team;
import com.volodymyrchikh.footballservice.repository.PlayerRepository;
import com.volodymyrchikh.footballservice.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamMapper teamMapper;
    private final PlayerMapper playerMapper;

    public TeamResponse save(TeamRequest teamRequest) {
        Team team = teamMapper.mapToTeam(teamRequest);
        return teamMapper.mapToTeamResponse(teamRepository.save(team));
    }

    public TeamResponse getById(Long teamId) {
        return teamRepository.findById(teamId)
                .map(teamMapper::mapToTeamResponse)
                .orElseThrow(() -> new TeamNotFoundException("Team of id=[%s] not found".formatted(teamId), teamId));
    }

    public Page<TeamResponse> getAll(Pageable pageable, Predicate filters) {
        return teamRepository.findAll(filters, pageable).map(teamMapper::mapToTeamResponse);
    }

    @Transactional
    public TeamResponse update(Long teamId, UpdateTeamRequest updateTeamRequest) {
        Team teamToUpdate = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException("Team of id=[%s] not found".formatted(teamId), teamId));

        Team newTeam = teamRepository.save(teamMapper.updateTeam(teamToUpdate, updateTeamRequest));
        return teamMapper.mapToTeamResponse(newTeam);
    }

    @Transactional
    public void delete(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Transactional
    public void addPlayerToTeam(Long playerId, Long teamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new TeamNotFoundException("Player of id=[%s] not found".formatted(playerId), playerId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team of id=[%s] not found".formatted(teamId), teamId));

        team.getPlayers().add(player);
        teamRepository.save(team);
    }

    public Page<TeamWithPlayersResponse> getPlayersByTeam(Long teamId, Pageable pageable) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team of id=[%s] not found".formatted(teamId), teamId));

        List<PlayerResponse> playerResponses = team.getPlayers().stream()
                .map(playerMapper::mapToPlayerResponse)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), playerResponses.size());
        Page<PlayerResponse> playerPage = new PageImpl<>(playerResponses.subList(start, end), pageable, playerResponses.size());

        List<TeamWithPlayersResponse> teamWithPlayersResponses = playerPage.stream()
                .map(playerResponse -> {
                    TeamWithPlayersResponse response = new TeamWithPlayersResponse();
                    response.setId(team.getId());
                    response.setName(team.getName());
                    response.setBalance(team.getBalance());
                    response.setCommissionRate(team.getCommissionRate());
                    response.setCreatedAt(team.getCreatedAt());
                    response.setUpdatedAt(team.getUpdatedAt());
                    response.setPlayers(new HashSet<>(playerPage.getContent()));
                    return response;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(teamWithPlayersResponses, pageable, playerPage.getTotalElements());
    }}
