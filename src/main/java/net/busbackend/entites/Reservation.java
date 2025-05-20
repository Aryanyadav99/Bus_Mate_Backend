package net.busbackend.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bus_reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "bus_schedule_id")
    private BusSchedule busSchedule;

    private Long timestamp;
    private String departureDate;
    private Integer totalSeatBooked;
    private  String reservationStatus;
    private String seatNumbers;
    private Integer totalPrice;
}
