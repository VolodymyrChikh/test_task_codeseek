package com.volodymyrchikh.footballservice.controller;

import com.querydsl.core.types.Predicate;
import com.volodymyrchikh.footballservice.dto.PlayerRequest;
import com.volodymyrchikh.footballservice.dto.PlayerResponse;
import com.volodymyrchikh.footballservice.dto.PlayerWithTeamsResponse;
import com.volodymyrchikh.footballservice.dto.UpdatePlayerRequest;
import com.volodymyrchikh.footballservice.model.Player;
import com.volodymyrchikh.footballservice.service.PlayerService;
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
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponse createPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        return playerService.save(playerRequest);
    }

    @GetMapping("/{id}")
    public PlayerResponse getPlayerById(@PathVariable Long id) {
        return playerService.getById(id);
    }

    @GetMapping
    public Page<PlayerResponse> getAllPlayers(@PageableDefault Pageable pageable,
                                              @QuerydslPredicate(root = Player.class) Predicate filter) {
        return playerService.getAll(pageable, filter);
    }

    @PutMapping("/{id}")
    public PlayerResponse updatePlayer(@PathVariable Long id,
                                       @Valid @RequestBody UpdatePlayerRequest updatePlayerRequest) {
        return playerService.update(id, updatePlayerRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.delete(id);
    }

    @PostMapping("/{playerId}/transfer-from/{fromTeamId}/to/{toTeamId}")
    public void transferPlayer(@PathVariable Long playerId, @PathVariable Long fromTeamId, @PathVariable Long toTeamId) {
        playerService.buyPlayer(playerId, fromTeamId, toTeamId);
    }

    @GetMapping("/{playerId}/teams")
    public Page<PlayerWithTeamsResponse> getTeamsByPlayer(@PathVariable Long playerId,
                                                          @PageableDefault Pageable pageable) {
        return playerService.getTeamsByPlayer(playerId, pageable);
    }
}
