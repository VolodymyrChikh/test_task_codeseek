package com.volodymyrchikh.footballservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.volodymyrchikh.footballservice.dto.common.AppConstants.NAME_REGEX;

@Getter
@Setter
public class TeamRequest {

    @NotBlank
    private String name;
    @Min(value = 0, message = "Balance must be greater than or equal 0")
    private Double balance;
    @Min(value = 0, message = "Commission rate must be greater than or equal to 0")
    @Max(value = 100, message = "Commission rate must be less than or equal to 100")
    private Double commissionRate;

}
