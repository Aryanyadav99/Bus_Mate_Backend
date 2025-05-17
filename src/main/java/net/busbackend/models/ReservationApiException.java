package net.busbackend.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class ReservationApiException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

    public ReservationApiException(HttpStatus status,String message){
        this.status=status;
        this.message=message;
    }

}
