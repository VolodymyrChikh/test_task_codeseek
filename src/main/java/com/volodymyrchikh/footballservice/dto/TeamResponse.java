package com.volodymyrchikh.footballservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamResponse {

    private Long id;
    private String name;
    private Double balance;
    private Double commissionRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
