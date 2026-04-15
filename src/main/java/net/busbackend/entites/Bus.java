package net.busbackend.entites;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;

    @Column(name = "bus_name", nullable = false)
    private String busName;

    @Column(name = "bus_type", nullable = false)
    private String busType;

    @Column(name = "bus_number", nullable = false, unique = true)
    private String busNumber;

    @Column(name = "total_seat", nullable = false)
    private Integer totalSeat;

    // for audit purpose

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
