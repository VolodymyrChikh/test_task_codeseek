package com.volodymyrchikh.footballservice.exception;

import lombok.Getter;

@Getter
public class PlayerNotInTeamException extends RuntimeException{

    private final Long playerId;
    private final Long teamId;

    public PlayerNotInTeamException(String message, Long playerId, Long teamId) {
        super(message);
        this.playerId = playerId;
        this.teamId = teamId;
    }

}
