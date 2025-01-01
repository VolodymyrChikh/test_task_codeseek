package com.volodymyrchikh.footballservice.dto;

import com.volodymyrchikh.footballservice.dto.common.PlayerPosition;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private PlayerPosition position;
    private Integer number;
    private Integer experienceInMonths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
