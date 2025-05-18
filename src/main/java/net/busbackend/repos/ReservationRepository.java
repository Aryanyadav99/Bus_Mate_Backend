package net.busbackend.repos;

import net.busbackend.entites.BusSchedule;
import net.busbackend.entites.Customer;
import net.busbackend.entites.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<List<Reservation>>findByCustomer(Customer customer);
    Optional<List<Reservation>>findByBusScheduleAndDepartureDate(BusSchedule busSchedule,String departureDate);
}
