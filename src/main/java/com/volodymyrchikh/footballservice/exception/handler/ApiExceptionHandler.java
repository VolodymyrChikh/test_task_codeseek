package com.volodymyrchikh.footballservice.exception.handler;

import com.volodymyrchikh.footballservice.dto.ErrorDetail;
import com.volodymyrchikh.footballservice.exception.PlayerNotFoundException;
import com.volodymyrchikh.footballservice.exception.PlayerNotInTeamException;
import com.volodymyrchikh.footballservice.exception.TeamNotFoundException;
import com.volodymyrchikh.footballservice.exception.TransferDeniedException;
import com.volodymyrchikh.footballservice.mapper.ErrorDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorDetailMapper errorDetailMapper;

    @ExceptionHandler({ PlayerNotFoundException.class })
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handlePlayerNotFoundException(PlayerNotFoundException ex, WebRequest request) {
        return getProblemDetail(
                NOT_FOUND,
                URI.create("about:blank"),
                "Player is not found!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler({ TeamNotFoundException.class })
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handleTeamNotFoundException(TeamNotFoundException ex, WebRequest request) {
        return getProblemDetail(
                NOT_FOUND,
                URI.create("about:blank"),
                "Player is not found!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler({ PlayerNotInTeamException.class })
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handlePlayerNotInTeamException(PlayerNotInTeamException ex, WebRequest request) {
        return getProblemDetail(
                NOT_FOUND,
                URI.create("about:blank"),
                "Player is not in such team!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler({ TransferDeniedException.class })
    @ResponseStatus(FORBIDDEN)
    public ProblemDetail handleTransferDeniedException(TransferDeniedException ex, WebRequest request) {
        return getProblemDetail(
                FORBIDDEN,
                URI.create("about:blank"),
                "Transfer is denied!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, URI type, String title, URI instance,
                                           List<ErrorDetail> details) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instance);
        problemDetail.setProperty("detail", details);
        return problemDetail;
    }

}
