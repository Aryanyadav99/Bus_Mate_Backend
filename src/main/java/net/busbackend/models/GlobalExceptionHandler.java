package net.busbackend.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.request.WebRequest;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Custom Exception
    @ExceptionHandler(ReservationApiException.class)
    public ResponseEntity<ErrorDetails> handleReservationApiException(
            ReservationApiException exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(exception.getStatus().value());
        errorDetails.setErrorMessage(exception.getMessage());
        errorDetails.setDevErrorMessage(request.getDescription(false));
        errorDetails.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    //  Access Denied (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(
            AccessDeniedException exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(HttpStatus.FORBIDDEN.value());
        errorDetails.setErrorMessage("Access Denied");
        errorDetails.setDevErrorMessage(exception.getMessage());
        errorDetails.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    // Global Exception (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setErrorMessage("Something went wrong");
        errorDetails.setDevErrorMessage(exception.getMessage());
        errorDetails.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}