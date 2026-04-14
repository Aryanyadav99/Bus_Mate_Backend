package net.busbackend.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "bus_schedule",
        indexes = {
                @Index(name = "idx_schedule_search", columnList = "bus_route_id, journey_date")
        }
)
public class BusSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    // MANY schedules -> ONE bus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    //  MANY schedules -> ONE route
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_route_id", nullable = false)
    private BusRoute busRoute;

    @Column(name = "journey_date", nullable = false)
    private LocalDate journeyDate;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private Integer ticketPrice;

    private Integer discount;

    private Integer processingFee;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
