package net.busbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    //This class especially build for handling error if we occur (custom error)
    private int errorCode;
    private String errorMessage;
    private String devErrorMessage;
    private Long timestamp;

}
