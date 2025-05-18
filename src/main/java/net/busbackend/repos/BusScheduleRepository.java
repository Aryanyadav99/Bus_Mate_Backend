package net.busbackend.repos;

import net.busbackend.entites.Bus;
import net.busbackend.entites.BusRoute;
import net.busbackend.entites.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusScheduleRepository extends JpaRepository<BusSchedule,Long> {
    Optional<List<BusSchedule>> findByBusRoute(BusRoute busRoute);

    Boolean existsByBusAndBusRouteAndDepartureTime(Bus bus, BusRoute busRoute,String date);
}
