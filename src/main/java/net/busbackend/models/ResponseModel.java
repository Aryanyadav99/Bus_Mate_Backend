package net.busbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {
    //generic t refer to accept any type of object either bus route and other
    private int statusCode;
    private String message;
    private T response;

}
