package com.volodymyrchikh.footballservice.mapper;

import com.volodymyrchikh.footballservice.dto.PlayerRequest;
import com.volodymyrchikh.footballservice.dto.PlayerResponse;
import com.volodymyrchikh.footballservice.dto.UpdatePlayerRequest;
import com.volodymyrchikh.footballservice.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PlayerMapper {

    PlayerResponse mapToPlayerResponse(Player player);

    Player mapToPlayerResponse(PlayerRequest playerRequest);

    Player updatePlayer(@MappingTarget Player playerToUpdate, UpdatePlayerRequest playerRequest);

}
