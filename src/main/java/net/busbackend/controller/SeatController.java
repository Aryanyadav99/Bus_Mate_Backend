package net.busbackend.controller;

import net.busbackend.DTO.SeatResponseDTO;
import net.busbackend.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatResponseDTO>> getAllSeatsForSchedule(@RequestParam Long scheduleId){
        return ResponseEntity.ok(seatService.getSeatsByScheduleId(scheduleId));
    }

    @PostMapping("/lock")
    public ResponseEntity<String> lockSeat(
            @RequestParam Long scheduleId,
            @RequestParam Integer seatNumber,
            @RequestParam String sessionId) {

        return ResponseEntity.ok(
                seatService.lockSeat(scheduleId, seatNumber, sessionId)
        );
    }
}
