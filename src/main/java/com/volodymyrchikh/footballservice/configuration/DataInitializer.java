package com.volodymyrchikh.footballservice.configuration;

import com.volodymyrchikh.footballservice.dto.PlayerRequest;
import com.volodymyrchikh.footballservice.dto.PlayerResponse;
import com.volodymyrchikh.footballservice.dto.TeamRequest;
import com.volodymyrchikh.footballservice.dto.TeamResponse;
import com.volodymyrchikh.footballservice.dto.common.PlayerPosition;
import com.volodymyrchikh.footballservice.service.PlayerService;
import com.volodymyrchikh.footballservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PlayerService playerService;
    private final TeamService teamService;

    @Override
    public void run(String... args) {
        TeamRequest team1 = new TeamRequest();
        team1.setName("Team A");
        team1.setBalance(1000000.0);
        team1.setCommissionRate(5.0);
        TeamResponse savedTeam1 = teamService.save(team1);

        TeamRequest team2 = new TeamRequest();
        team2.setName("Team B");
        team2.setBalance(2000000.0);
        team2.setCommissionRate(10.0);
        TeamResponse savedTeam2 = teamService.save(team2);

        PlayerRequest player1 = new PlayerRequest();
        player1.setFirstName("John");
        player1.setLastName("Doe");
        player1.setAge(25);
        player1.setNumber(10);
        player1.setPosition(PlayerPosition.FORWARD);
        player1.setExperienceInMonths(60);
        PlayerResponse savedPlayer1 = playerService.save(player1);

        PlayerRequest player2 = new PlayerRequest();
        player2.setFirstName("Jane");
        player2.setLastName("Smith");
        player2.setAge(22);
        player2.setNumber(5);
        player2.setPosition(PlayerPosition.DEFENDER);
        player2.setExperienceInMonths(36);
        PlayerResponse savedPlayer2 = playerService.save(player2);

        teamService.addPlayerToTeam(savedPlayer1.getId(), savedTeam1.getId());
        teamService.addPlayerToTeam(savedPlayer2.getId(), savedTeam2.getId());
    }
}