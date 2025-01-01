package com.volodymyrchikh.footballservice.dto;

import com.volodymyrchikh.footballservice.dto.common.PlayerPosition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.volodymyrchikh.footballservice.dto.common.AppConstants.NAME_REGEX;

@Getter
@Setter
public class PlayerRequest {

    @Pattern(regexp = NAME_REGEX, message = "Must start with a capital letter!")
    private String firstName;
    @Pattern(regexp = NAME_REGEX, message = "Must start with a capital letter!")
    private String lastName;
    @Min(value = 18, message = "Player must be at least 18 years old!")
    private Integer age;
    private PlayerPosition position;
    @Min(value = 1, message = "Player must have a number greater than 0!")
    private Integer number;
    @Min(value = 0, message = "Player must have at least 0 months of experience!")
    private Integer experienceInMonths;

}
