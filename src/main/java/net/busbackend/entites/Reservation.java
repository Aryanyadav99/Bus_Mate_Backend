package net.busbackend.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String reservationId;
    private Customer customer;
    private BusSchedule busSchedule;
    private Long  timeStamp;
    private String departureDate;
    private Integer totalSeatBooked;
    private  String reservationStatus;
    private String seatNumber;
    private Integer totalPrice;
}
