package net.busbackend.controller;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;
import net.busbackend.DTO.UserResponseDTO;
import net.busbackend.models.ReservationApiException;
import net.busbackend.security.SecurityUtil;
import net.busbackend.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private SecurityUtil securityUtil;

    // Helper method
    private void validateAdmin() {
        UserResponseDTO currentUser = securityUtil.getCurrentUserDto();

        if (currentUser == null) {
            throw new ReservationApiException(HttpStatus.UNAUTHORIZED, "Login First");
        }

        if (!currentUser.isAdmin()) {
            throw new ReservationApiException(HttpStatus.FORBIDDEN, "You are not Admin");
        }
    }

    // Create Bus
    @PostMapping
    public ResponseEntity<BusResponseDto> addBus(
            @Valid @RequestBody BusRequestDto busRequestDto) {

        validateAdmin();

        BusResponseDto savedBus = busService.addBus(busRequestDto);

        return new ResponseEntity<>(savedBus, HttpStatus.CREATED);
    }

    //  Get All Buses
    @GetMapping
    public ResponseEntity<List<BusResponseDto>> getAllBus() {

        validateAdmin();

        List<BusResponseDto> buses = busService.getAllBus();

        return ResponseEntity.ok(buses);
    }

    //  Get Bus by ID
    @GetMapping("/{id}")
    public ResponseEntity<BusResponseDto> getBusById(@PathVariable Long id) {

        validateAdmin();

        BusResponseDto bus = busService.getBusById(id);

        return ResponseEntity.ok(bus);
    }

    //  Update Bus
    @PutMapping("/{id}")
    public ResponseEntity<BusResponseDto> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusRequestDto requestDto) {

        validateAdmin();

        BusResponseDto updatedBus = busService.updateBus(id, requestDto);

        return ResponseEntity.ok(updatedBus);
    }

    //  Delete Bus
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {

        validateAdmin();

        busService.deleteBus(id);

        return ResponseEntity.noContent().build();
    }
}