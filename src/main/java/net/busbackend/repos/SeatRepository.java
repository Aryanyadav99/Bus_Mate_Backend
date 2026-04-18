package net.busbackend.repos;

import net.busbackend.entites.BusSchedule;
import net.busbackend.entites.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findBySchedule(BusSchedule schedule);

    Optional<Seat> findByScheduleAndSeatNumber(BusSchedule schedule, Integer seatNo);
}
