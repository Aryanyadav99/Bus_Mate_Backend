package net.busbackend.controller;

import net.busbackend.DTO.UserResponseDTO;
import net.busbackend.entites.BusSchedule;
import net.busbackend.models.ReservationApiException;
import net.busbackend.models.ResponseModel;
import net.busbackend.security.SecurityUtil;
import net.busbackend.services.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class BusScheduleController {

    @Autowired
    private BusScheduleService busScheduleService;

    @Autowired
    private SecurityUtil securityUtil;

    private void validateAdmin() {
        UserResponseDTO currentUser = securityUtil.getCurrentUserDto();

        if (currentUser == null) {
            throw new ReservationApiException(HttpStatus.UNAUTHORIZED, "Login First");
        }

        if (!currentUser.isAdmin()) {
            throw new ReservationApiException(HttpStatus.FORBIDDEN, "You are not Admin");
        }
    }
    private void validateUser() {
        UserResponseDTO currentUser = securityUtil.getCurrentUserDto();

        if (currentUser == null) {
            throw new ReservationApiException(HttpStatus.UNAUTHORIZED, "Login First");
        }
    }
    @PostMapping("/add")
    public ResponseModel<BusSchedule> addBusSchedule(
            @RequestBody BusSchedule busSchedule){
        final BusSchedule schedule=busScheduleService.addSchedule(busSchedule);
        return new ResponseModel<>(HttpStatus.OK.value(),"Schedule Saved" , schedule);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusSchedule>> getAllSchedules(){
        return ResponseEntity.ok(busScheduleService.getAllBusSchedules());
    }
    @GetMapping("/{routeName}")
    public ResponseEntity<List<BusSchedule>> getBusScheduleByRouteName(@PathVariable(name="routeName") String routeName){
        return ResponseEntity.ok(busScheduleService.getSchedulesByRoute(routeName));
    }
}
