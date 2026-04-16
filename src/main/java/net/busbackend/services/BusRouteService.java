package net.busbackend.services;
import java.util.*;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;
import net.busbackend.DTO.RouteRequestDTO;
import net.busbackend.DTO.RouteResponseDTO;
import net.busbackend.entites.BusRoute;
import org.springframework.stereotype.Service;


public interface BusRouteService {
    RouteResponseDTO addRoute(RouteRequestDTO busRequestDto);
    List<RouteResponseDTO> getAllBusRoutes();

    RouteResponseDTO getRouteByRouteName(String routeName);
    RouteResponseDTO getRouteByCityFromAndCityTo(String cityFrom , String cityTo);
}
