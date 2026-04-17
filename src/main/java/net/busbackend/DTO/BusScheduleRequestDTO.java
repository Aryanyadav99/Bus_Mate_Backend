package net.busbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusScheduleRequestDTO {
    @NotNull
    private Long busId;
    @NotNull
    private Long routeId;
    @NotNull
    private LocalDate journeyDate;
    @NotNull
    @Positive
    private Integer ticketPrice;
    private Integer discount;
    private Integer processingFee;

    @NotNull
    private LocalTime departureTime;
    @NotNull
    private LocalTime arrivalTime;
}
