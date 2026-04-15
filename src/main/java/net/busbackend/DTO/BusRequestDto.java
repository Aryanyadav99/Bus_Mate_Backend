package net.busbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusRequestDto {
    @NotBlank
    private String busName;
    @NotBlank
    private String busType;
    @NotNull
    private int totalSeats;
    @NotBlank
    private String busNumber;
}
