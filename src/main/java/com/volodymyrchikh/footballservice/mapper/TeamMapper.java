package com.volodymyrchikh.footballservice.mapper;

import com.volodymyrchikh.footballservice.dto.PlayerResponse;
import com.volodymyrchikh.footballservice.dto.TeamRequest;
import com.volodymyrchikh.footballservice.dto.TeamResponse;
import com.volodymyrchikh.footballservice.dto.UpdateTeamRequest;
import com.volodymyrchikh.footballservice.model.Player;
import com.volodymyrchikh.footballservice.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface TeamMapper {

    TeamResponse mapToTeamResponse(Team team);

    Team mapToTeam(TeamRequest teamRequest);

    Team updateTeam(@MappingTarget Team teamToUpdate, UpdateTeamRequest teamRequest);

}