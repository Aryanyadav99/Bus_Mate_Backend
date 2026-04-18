package net.busbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {

    private Long scheduleId;
    private List<Integer> seatNumbers;
    private String sessionId;
 // we dont need to pass because we are setting it in the controller
    private String userId;

    private String passengerName;
    private String passengerEmail;
    private String passengerMobile;
}
