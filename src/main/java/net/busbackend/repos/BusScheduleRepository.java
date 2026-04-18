package net.busbackend.repos;

import jakarta.validation.constraints.NotNull;
import net.busbackend.entites.Bus;
import net.busbackend.entites.BusRoute;
import net.busbackend.entites.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BusScheduleRepository extends JpaRepository<BusSchedule,Long> {
    Optional<List<BusSchedule>> findByBusRoute(BusRoute busRoute);
    boolean existsByBusAndBusRouteAndDepartureTime(Bus bus, BusRoute busRoute, java.time.LocalTime departureTime);

    boolean existsByBusAndBusRouteAndJourneyDateAndDepartureTime(Bus bus, BusRoute route, @NotNull LocalDate journeyDate, @NotNull LocalTime departureTime);
}
