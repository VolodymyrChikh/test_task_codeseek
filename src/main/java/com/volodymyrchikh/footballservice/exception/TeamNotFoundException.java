package com.volodymyrchikh.footballservice.exception;

import lombok.Getter;

@Getter
public class TeamNotFoundException extends RuntimeException {

    private final Long teamId;

    public TeamNotFoundException(String message, Long teamId) {
        super(message);
        this.teamId = teamId;
    }

}