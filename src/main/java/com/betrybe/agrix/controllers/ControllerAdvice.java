package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.ErrorDto;
import com.betrybe.agrix.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller Advice.
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
  
  /**
  * Exception Handler.
  */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> notFound(NotFoundException exception) {
    ErrorDto errorDto = new ErrorDto(exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }
}
