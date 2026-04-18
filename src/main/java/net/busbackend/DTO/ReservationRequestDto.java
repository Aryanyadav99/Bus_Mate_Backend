package net.busbackend.DTO;

import java.util.List;

public class ReservationRequestDto {

    private Long scheduleId;
    private List<Integer> seatNumbers;
    private String sessionId;

    private Long userId;

    private String passengerName;
    private String passengerEmail;
    private String passengerMobile;
}
