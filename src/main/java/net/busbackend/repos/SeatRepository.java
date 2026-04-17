package net.busbackend.repos;

import net.busbackend.entites.BusSchedule;
import net.busbackend.entites.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findBySchedule(BusSchedule schedule);
}
