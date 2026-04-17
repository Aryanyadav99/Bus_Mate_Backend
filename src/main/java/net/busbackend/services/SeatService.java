package net.busbackend.services;

import net.busbackend.DTO.SeatResponseDTO;

import java.util.List;

public interface SeatService {
    List<SeatResponseDTO> getSeatsByScheduleId(Long scheduleId);

    String lockSeat(Long scheduleId, Integer seatNumber, String sessionId);
}
