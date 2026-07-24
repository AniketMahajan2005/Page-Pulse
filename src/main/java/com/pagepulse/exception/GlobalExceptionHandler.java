package com.pagepulse.exception;

import com.pagepulse.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String url = request.getParameter("url");
        ErrorResponse errorResponse = new ErrorResponse("Bad Request", ex.getMessage(), url);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        String url = request.getParameter("url");
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        
        if (message != null) {
            if (message.contains("timed out")) {
                status = HttpStatus.REQUEST_TIMEOUT;
            } else if (message.contains("not point to an HTML")) {
                status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            }
        }
        
        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(), message, url);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        String url = request.getParameter("url");
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", ex.getMessage(), url);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
