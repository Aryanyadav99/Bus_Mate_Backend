package net.busbackend.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.busbackend.util.ReservationStatus;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    //MANY reservations -> ONE customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // MANY reservations ->  ONE schedule
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_schedule_id", nullable = false)
    private BusSchedule busSchedule;

    // relation with seats
    @ManyToMany
    @JoinTable(
            name = "reservation_seat",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    @Column(nullable = false)
    private Integer totalSeatBooked;

    @Column(nullable = false)
    private Integer totalPrice;

    // ENUM instead of string
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}