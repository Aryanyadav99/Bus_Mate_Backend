package net.busbackend.services;

import net.busbackend.entites.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation addReservation(Reservation reservation);
    List<Reservation> getAllReservation();
    List<Reservation>getReservationByScheduleAndDepartureDate(Long schedule,String departureDate);
    List<Reservation>getReservationByMobile(String mobile);
}
