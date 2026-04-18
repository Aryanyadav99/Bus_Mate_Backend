package net.busbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {

    private Long reservationId;
    private String seatNumbers;
    private Integer totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
