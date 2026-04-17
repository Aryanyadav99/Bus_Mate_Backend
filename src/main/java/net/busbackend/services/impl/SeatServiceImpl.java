package net.busbackend.services.impl;

import net.busbackend.DTO.SeatResponseDTO;
import net.busbackend.entites.BusSchedule;
import net.busbackend.entites.Seat;
import net.busbackend.repos.BusScheduleRepository;
import net.busbackend.repos.SeatRepository;
import net.busbackend.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    BusScheduleRepository busScheduleRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<SeatResponseDTO> getSeatsByScheduleId(Long scheduleId) {
        BusSchedule schedule = busScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        List<Seat> seats = seatRepository.findBySchedule(schedule);

        return seats.stream().map(seat -> {

            String status = "AVAILABLE";

            if (seat.getIsBooked()) {
                status = "BOOKED";
            }
            String key = "seat_lock:" + scheduleId + ":" + seat.getSeatNumber();
            if (redisTemplate.hasKey(key)) {
                status = "LOCKED";
            }
            return new SeatResponseDTO(
                    seat.getSeatNumber(),
                    status
            );

        }).toList();
    }

    @Override
    public String lockSeat(Long scheduleId, Integer seatNumber, String sessionId) {

        String key = "seat_lock:" + scheduleId + ":" + seatNumber;

        // Try to acquire lock
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, sessionId, Duration.ofMinutes(5));

        if (Boolean.FALSE.equals(success)) {
            String lockedBy = redisTemplate.opsForValue().get(key);
            if (sessionId.equals(lockedBy)) {
                return "Seat already locked by you";
            }
            throw new RuntimeException("Seat already locked by another user");
        }
        return "Seat locked successfully";
    }
}
