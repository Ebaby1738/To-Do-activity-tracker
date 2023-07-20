package com.activityTracker.tracker.dto.responseDto;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
    private String debugMessage;
}
