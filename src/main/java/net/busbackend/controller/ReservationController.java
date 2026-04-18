package net.busbackend.controller;

import net.busbackend.DTO.ReservationRequestDto;
import net.busbackend.DTO.ReservationResponseDto;
import net.busbackend.DTO.UserResponseDTO;
import net.busbackend.entites.Reservation;
import net.busbackend.entites.User;
import net.busbackend.models.ReservationApiException;
import net.busbackend.models.ResponseModel;
import net.busbackend.security.SecurityUtil;
import net.busbackend.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SecurityUtil securityUtil;

    private void validateUser() {
        UserResponseDTO currentUser = securityUtil.getCurrentUserDto();

        if (currentUser == null) {
            throw new ReservationApiException(HttpStatus.UNAUTHORIZED, "Login First");
        }
    }
    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody ReservationRequestDto dto) {
        validateUser();
        User user= securityUtil.getCurrentUser();
        dto.setUserId(user.getId());
        return ResponseEntity.ok(
                reservationService.createReservation(dto)
        );
    }
}