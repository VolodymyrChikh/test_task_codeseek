package com.volodymyrchikh.footballservice.controller;

import com.querydsl.core.types.Predicate;
import com.volodymyrchikh.footballservice.dto.*;
import com.volodymyrchikh.footballservice.model.Team;
import com.volodymyrchikh.footballservice.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createTeam(@Valid @RequestBody TeamRequest teamRequest) {
        return teamService.save(teamRequest);
    }

    @GetMapping("/{id}")
    public TeamResponse getTeamById(@PathVariable Long id) {
        return teamService.getById(id);
    }

    @GetMapping
    public Page<TeamResponse> getAllTeams(@PageableDefault Pageable pageable,
                                          @QuerydslPredicate(root = Team.class) Predicate filter) {
        return teamService.getAll(pageable, filter);
    }

    @PutMapping("/{id}")
    public TeamResponse updateTeam(@PathVariable Long id,
                                   @Valid @RequestBody UpdateTeamRequest updateTeamRequest) {
        return teamService.update(id, updateTeamRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.delete(id);
    }


    @PostMapping("/{teamId}/players/{playerId}")
    public void addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        teamService.addPlayerToTeam(playerId, teamId);
    }

    @GetMapping("/{teamId}/players")
    public Page<TeamWithPlayersResponse> getPlayersByTeam(@PathVariable Long teamId,
                                                          @PageableDefault Pageable pageable) {
        return teamService.getPlayersByTeam(teamId, pageable);
    }
}