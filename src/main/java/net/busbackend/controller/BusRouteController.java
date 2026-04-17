package net.busbackend.controller;
import jakarta.validation.Valid;
import net.busbackend.DTO.RouteRequestDTO;
import net.busbackend.DTO.RouteResponseDTO;
import net.busbackend.DTO.UserResponseDTO;
import net.busbackend.entites.BusRoute;
import net.busbackend.models.ReservationApiException;
import net.busbackend.models.ResponseModel;
import net.busbackend.security.SecurityUtil;
import net.busbackend.services.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class BusRouteController {

    @Autowired
    private BusRouteService busRouteService;
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
    public ResponseModel<RouteResponseDTO> addRoute(@Valid  @RequestBody RouteRequestDTO routeRequestDTO){
        validateAdmin();
        RouteResponseDTO routeResponseDTO=busRouteService.addRoute(routeRequestDTO);
        return new ResponseModel<>(HttpStatus.OK.value(), "Route Saved" ,routeResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RouteResponseDTO>> getAllBusRoutes(){
        validateAdmin();
        return ResponseEntity.ok(busRouteService.getAllBusRoutes());
    }

    @GetMapping("/query")
    public ResponseEntity<RouteResponseDTO> getRouteByCityFromAndCityTo(
            @RequestParam String cityFrom,
            @RequestParam String cityTo

    ){
        validateAdmin();
        return ResponseEntity.ok(busRouteService.getRouteByCityFromAndCityTo(cityFrom,cityTo));
    }

}
