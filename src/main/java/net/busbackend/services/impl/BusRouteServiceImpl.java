package net.busbackend.services.impl;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;
import net.busbackend.DTO.RouteRequestDTO;
import net.busbackend.DTO.RouteResponseDTO;
import net.busbackend.entites.Bus;
import net.busbackend.entites.BusRoute;
import net.busbackend.models.ReservationApiException;
import net.busbackend.repos.BusRepository;
import net.busbackend.repos.BusRouteRepository;
import net.busbackend.services.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusRouteServiceImpl implements BusRouteService {

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Override
    public RouteResponseDTO addRoute(RouteRequestDTO routeRequestDTO) {
        BusRoute route=mapToEntity(routeRequestDTO);
        busRouteRepository.save(route);
        return mapToDto(route);
    }

    @Override
    public List<RouteResponseDTO> getAllBusRoutes() {
        List<BusRoute>routes=busRouteRepository.findAll();
        return routes.stream().map(this::mapToDto).toList();
    }

    @Override
    public RouteResponseDTO getRouteByRouteName(String routeName) {
        Optional<BusRoute>route= busRouteRepository.findByRouteName(routeName);
        if(route.get()==null){
            throw new ReservationApiException(HttpStatus.BAD_REQUEST,"No Such Route Found");
        }
        return mapToDto(route.get());
    }

    @Override
    public RouteResponseDTO getRouteByCityFromAndCityTo(String cityFrom, String cityTo) {
        Optional<BusRoute>route= busRouteRepository.findByCityFromAndCityTo(cityFrom,cityTo);
        if(route.get()==null) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "No Such Route Found");
        }
        return mapToDto(route.get());
    }
    private BusRoute mapToEntity(RouteRequestDTO dto) {
        BusRoute route=new BusRoute();
        route.setCityFrom(dto.getCityFrom());
        route.setCityTo(dto.getCityTo());
        route.setDistanceInKm(dto.getDistanceInKm());
        return route;
    }
    private RouteResponseDTO mapToDto(BusRoute route) {
        return new RouteResponseDTO(
                route.getRouteId(),
                route.getCityFrom(),
                route.getCityTo(),
                route.getDistanceInKm()
        );
    }
}
