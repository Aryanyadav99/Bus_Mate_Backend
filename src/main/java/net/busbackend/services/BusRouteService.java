package net.busbackend.services;
import java.util.*;

import net.busbackend.DTO.RouteRequestDTO;
import net.busbackend.DTO.RouteResponseDTO;



public interface BusRouteService {
    RouteResponseDTO addRoute(RouteRequestDTO busRequestDto);
    List<RouteResponseDTO> getAllBusRoutes();

    RouteResponseDTO getRouteByCityFromAndCityTo(String cityFrom , String cityTo);
}
