package net.busbackend.entites;

import javax.persistence.*;

@Entity
@Table(
        name = "seat",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_seat_per_schedule",
                        columnNames = {"schedule_id", "seat_number"}
                )
        }
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private BusSchedule schedule;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    private Boolean isBooked = false;

    @Version  // help to handle concurrent booking (optimistic locking)
    private Long version;
}
