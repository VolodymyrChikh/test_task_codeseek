package com.volodymyrchikh.footballservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TeamWithPlayersResponse {

    private Long id;
    private String name;
    private Double balance;
    private Double commissionRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<PlayerResponse> players;

}
