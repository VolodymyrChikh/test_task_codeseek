package com.volodymyrchikh.footballservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PlayerWithTeamsResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer experienceInMonths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<TeamResponse> teams;

}