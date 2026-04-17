package net.busbackend.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BusScheduleResponseDTO {
    private Long scheduleId;

    private Long busId;
    private String busName;

    private Long routeId;
    private String cityFrom;
    private String cityTo;

    private LocalDate journeyDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private Integer ticketPrice;
    private Integer discount;
    private Integer processingFee;
}
