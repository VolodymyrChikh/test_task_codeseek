package com.volodymyrchikh.footballservice.mapper;

import com.volodymyrchikh.footballservice.dto.ErrorDetail;
import com.volodymyrchikh.footballservice.exception.PlayerNotFoundException;
import com.volodymyrchikh.footballservice.exception.PlayerNotInTeamException;
import com.volodymyrchikh.footballservice.exception.TeamNotFoundException;
import com.volodymyrchikh.footballservice.exception.TransferDeniedException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ErrorDetailMapper {

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Player of id=[%s] is not found\"" +
            ".formatted(ex.getPlayerId()))")
    ErrorDetail from(PlayerNotFoundException ex);

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Team of id=[%s] is not found\"" +
            ".formatted(ex.getTeamId()))")
    ErrorDetail from(TeamNotFoundException ex);

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Player of id=[%s] is not in the team of id=[%s]\"" +
            ".formatted(ex.getPlayerId(), ex.getTeamId()))")
    ErrorDetail from(PlayerNotInTeamException ex);

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Transfer is denied: %s\"" +
            ".formatted(ex.getMessage()))")
    ErrorDetail from(TransferDeniedException ex);

}
