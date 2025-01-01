package com.volodymyrchikh.footballservice.service;

import com.querydsl.core.types.Predicate;
import com.volodymyrchikh.footballservice.dto.*;
import com.volodymyrchikh.footballservice.exception.PlayerNotFoundException;
import com.volodymyrchikh.footballservice.exception.PlayerNotInTeamException;
import com.volodymyrchikh.footballservice.exception.TeamNotFoundException;
import com.volodymyrchikh.footballservice.exception.TransferDeniedException;
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
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerMapper playerMapper;
    private final TeamMapper teamMapper;

    public PlayerResponse save(PlayerRequest playerRequest) {
        Player player = playerMapper.mapToPlayerResponse(playerRequest);
        return playerMapper.mapToPlayerResponse(playerRepository.save(player));
    }

    public PlayerResponse getById(Long playerId) {
        return playerRepository.findById(playerId)
                .map(playerMapper::mapToPlayerResponse)
                .orElseThrow(() -> new PlayerNotFoundException("Player of id=[%s] not found".formatted(playerId), playerId));
    }

    public Page<PlayerResponse> getAll(Pageable pageable, Predicate filters) {
        return playerRepository.findAll(filters, pageable).map(playerMapper::mapToPlayerResponse);
    }

    @Transactional
    public PlayerResponse update(Long playerId, UpdatePlayerRequest updatePlayerRequest) {
        Player playerToUpdate = playerRepository.findById(playerId).orElseThrow(
                () -> new PlayerNotFoundException("Player of id=[%s] not found".formatted(playerId), playerId));

        Player newPlayer = playerRepository.save(playerMapper.updatePlayer(playerToUpdate, updatePlayerRequest));
        return playerMapper.mapToPlayerResponse(newPlayer);
    }

    @Transactional
    public void delete(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public void buyPlayer(Long playerId, Long fromTeamId, Long toTeamId) {
        Assert.notNull(playerId, "Player ID must not be null");
        Assert.notNull(fromTeamId, "From Team ID must not be null");
        Assert.notNull(toTeamId, "To Team ID must not be null");

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found", playerId));
        Team toTeam = teamRepository.findById(toTeamId)
                .orElseThrow(() -> new TeamNotFoundException("Team of id=[%s] not found".formatted(toTeamId), toTeamId));
        Team fromTeam = teamRepository.findById(fromTeamId)
                .orElseThrow(() -> new TeamNotFoundException("Team of id=[%s] not found".formatted(fromTeamId), fromTeamId));

        if (!isPlayerInTeam(playerId, fromTeamId)) {
            throw new PlayerNotInTeamException("Player of id=[%s] is not in the team of id=[%s]"
                    .formatted(playerId, fromTeamId), playerId, fromTeamId);
        }

        Double transferCost = calculateTransferCost(player);
        Double totalCost = transferCost * (1 + fromTeam.getCommissionRate() / 100.0);

        if (toTeam.getBalance() < totalCost) {
            throw new TransferDeniedException("Insufficient funds for the transfer");
        }

        fromTeam.setBalance(fromTeam.getBalance() + transferCost);
        toTeam.setBalance(toTeam.getBalance() - totalCost);

        fromTeam.getPlayers().remove(player);
        toTeam.getPlayers().add(player);

        teamRepository.save(fromTeam);
        teamRepository.save(toTeam);

        player.getTeams().remove(fromTeam);
        player.getTeams().add(toTeam);

        playerRepository.save(player);
    }

    private Double calculateTransferCost(Player player) {
        return player.getExperienceInMonths() * 100000.0 / player.getAge();
    }

    public Page<PlayerWithTeamsResponse> getTeamsByPlayer(Long playerId, Pageable pageable) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player of id=[%s] not found".formatted(playerId), playerId));

        List<TeamResponse> teamResponses = player.getTeams().stream()
                .map(teamMapper::mapToTeamResponse)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), teamResponses.size());
        Page<TeamResponse> teamPage = new PageImpl<>(teamResponses.subList(start, end), pageable, teamResponses.size());

        List<PlayerWithTeamsResponse> playerWithTeamsResponses = teamPage.stream()
                .map(teamResponse -> {
                    PlayerWithTeamsResponse response = new PlayerWithTeamsResponse();
                    response.setId(player.getId());
                    response.setFirstName(player.getFirstName());
                    response.setLastName(player.getLastName());
                    response.setAge(player.getAge());
                    response.setExperienceInMonths(player.getExperienceInMonths());
                    response.setCreatedAt(player.getCreatedAt());
                    response.setUpdatedAt(player.getUpdatedAt());
                    response.setTeams(new HashSet<>(teamPage.getContent()));
                    return response;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(playerWithTeamsResponses, pageable, teamPage.getTotalElements());
    }

    private boolean isPlayerInTeam(Long playerId, Long teamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player of id=[%s] not found".formatted(playerId), playerId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team of id=[%s] not found".formatted(teamId), teamId));
        return player.getTeams().contains(team);
    }

}
