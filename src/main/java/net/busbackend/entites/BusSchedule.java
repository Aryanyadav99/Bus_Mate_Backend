package net.busbackend.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusSchedule {
    private Long scheduleId;
    private  Bus bus;
    private  BusRoute busRoute;
    private  String departureTime;
    private Integer ticketPrice;
    private Integer discount;
    private Integer processingFee;

}
