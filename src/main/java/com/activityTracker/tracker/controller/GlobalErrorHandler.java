package com.activityTracker.tracker.controller;


import com.activityTracker.tracker.exceptions.CustomAppException;
import com.activityTracker.tracker.dto.responseDto.ErrorResponse;
import com.activityTracker.tracker.exceptions.ResourceAlreadyExistException;
import com.activityTracker.tracker.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> handleResourceAlreadyExistException(final ResourceAlreadyExistException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setDebugMessage("Resource already exist");
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<?> handleCustomAppException(final CustomAppException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setDebugMessage("Task does not exist");

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(final ResourceNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setDebugMessage("please check your login details and try again");
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}
