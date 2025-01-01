package com.volodymyrchikh.footballservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTeamRequest {

    private String name;
    private Double balance;
    private Double commissionRate;

}