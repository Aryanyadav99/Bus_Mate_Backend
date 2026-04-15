package net.busbackend.entites;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bus_route",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"city_from", "city_to"})
    })
public class BusRoute {

    // fixes - remove route name because it's a combination of city from and city to which cause redundancy  and provide the
    // unique constraints on pair city from and city to because we don't want to have duplicate routes between same cities
    // it also creates indexes so tc became log(n) form n
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long routeId;

    @Column(name = "city_from",nullable = false)
    private String cityFrom;

    @Column(name = "city_to",nullable = false)
    private String cityTo;

    @Column(name = "distance_in_km",nullable = false)
    private Double distanceInKm;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
