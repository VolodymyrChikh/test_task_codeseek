package com.volodymyrchikh.footballservice.exception;

import lombok.Getter;

@Getter
public class PlayerNotFoundException extends RuntimeException {

    private final Long playerId;

    public PlayerNotFoundException(String message, Long playerId) {
        super(message);
        this.playerId = playerId;
    }

}
