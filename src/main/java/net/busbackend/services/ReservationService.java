package net.busbackend.services;

import net.busbackend.DTO.ReservationRequestDto;
import net.busbackend.DTO.ReservationResponseDto;
import net.busbackend.entites.Reservation;

import java.util.List;

public interface ReservationService {

    ReservationResponseDto createReservation(ReservationRequestDto dto);
}
