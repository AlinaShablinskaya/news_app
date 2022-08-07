package ru.clevertec.newsapp.controllers.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.newsapp.dto.exception.ResponseError;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

/**
 * Exception Handling for RestControllers
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Handles EntityExistsException if specified entity already exists
     * @param exception throws EntityExistsException if the entity already exists
     * @return ResponseError with specified message information about exception in body
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseError handleEntityAlreadyExistException(EntityExistsException exception) {
        log.error(exception.toString());
        return new ResponseError(LocalDateTime.now(), "Entity already exist");
    }

    /**
     * Handles EntityNotFoundException if specified entity not found
     * @param exception throws EntityNotFoundException if the entity not found
     * @return ResponseError with specified message information about exception in body
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.toString());
        return new ResponseError(LocalDateTime.now(), "Entity not found");
    }
}
