package net.busbackend.services.impl;

import net.busbackend.DTO.BusScheduleRequestDTO;
import net.busbackend.DTO.BusScheduleResponseDTO;
import net.busbackend.DTO.RouteRequestDTO;
import net.busbackend.DTO.RouteResponseDTO;
import net.busbackend.entites.Bus;
import net.busbackend.entites.BusRoute;
import net.busbackend.entites.BusSchedule;
import net.busbackend.entites.Seat;
import net.busbackend.models.ReservationApiException;
import net.busbackend.repos.BusRepository;
import net.busbackend.repos.BusRouteRepository;
import net.busbackend.repos.BusScheduleRepository;
import net.busbackend.repos.SeatRepository;
import net.busbackend.services.BusScheduleService;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusScheduleServiceImpl implements BusScheduleService {

    @Autowired
    private BusScheduleRepository busScheduleRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private  BusRouteRepository routeRepository;
    @Autowired
    private BusRouteRepository busRouteRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public BusScheduleResponseDTO addSchedule(BusScheduleRequestDTO busScheduleRequestDTO) throws  ReservationApiException {
        // find the bus
        Bus bus= busRepository.findById(busScheduleRequestDTO.getBusId())
                .orElseThrow(()->new ReservationApiException(HttpStatus.BAD_REQUEST,"Bus Not Found"));
        // find the route
        BusRoute route= busRouteRepository.findById(busScheduleRequestDTO.getRouteId())
                .orElseThrow(()->new ReservationApiException(HttpStatus.BAD_REQUEST,"Route Not Found"));
        // check arrival time is after departure time
        if(busScheduleRequestDTO.getArrivalTime().isBefore(busScheduleRequestDTO.getDepartureTime())) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Arrival Time Must Be After Departure Time");
        }
        // check duplicate
        if(busScheduleRepository.existsByBusAndBusRouteAndJourneyDateAndDepartureTime(bus,route,busScheduleRequestDTO.getJourneyDate(),busScheduleRequestDTO.getDepartureTime())) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Already Exist");
        }
        // create the schedule
        BusSchedule schedule=new BusSchedule();
        schedule.setBus(bus);
        schedule.setBusRoute(route);
        schedule.setDepartureTime(busScheduleRequestDTO.getDepartureTime());
        schedule.setArrivalTime(busScheduleRequestDTO.getArrivalTime());
        schedule.setJourneyDate(busScheduleRequestDTO.getJourneyDate());
        schedule.setTicketPrice(busScheduleRequestDTO.getTicketPrice());
        schedule.setDiscount(busScheduleRequestDTO.getDiscount());
        schedule.setProcessingFee(busScheduleRequestDTO.getProcessingFee());
        busScheduleRepository.save(schedule);

        // now we have a schedule so we have to create seats for that schedule
        List<Seat>seats=new ArrayList<>();
        for(int i=1;i<=bus.getTotalSeat();i++){
            Seat seat=new Seat();
            seat.setSeatNumber(i);
            seat.setSchedule(schedule);
            seat.setIsBooked(false);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);

        return mapToDto(schedule);
    }

    @Override
    public List<BusScheduleResponseDTO> getAllBusSchedules() {
        List<BusSchedule> schedules= busScheduleRepository.findAll();
        return schedules.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<BusScheduleResponseDTO> getSchedulesByRoute(String cityFrom, String cityTo) {
        Optional<BusRoute>route=busRouteRepository.findByCityFromAndCityTo(cityFrom,cityTo);
        if(route.isEmpty()) {
            throw new ReservationApiException(HttpStatus.NOT_FOUND, "Route Not Found");
        }
        BusRoute currRoute=route.get();
        Optional<List<BusSchedule>> schedules=busScheduleRepository.findByBusRoute(currRoute);
        if(schedules.isEmpty()) {
            throw new ReservationApiException(HttpStatus.NOT_FOUND, "No Schedules Found For This Route");
        }
        return schedules.get().stream().map(this::mapToDto).toList();
    }

    private  BusScheduleResponseDTO mapToDto(BusSchedule schedule) {
        return new BusScheduleResponseDTO(
                schedule.getScheduleId(),
                schedule.getBus().getBusId(),
                schedule.getBus().getBusName(),
                schedule.getBusRoute().getRouteId(),
                schedule.getBusRoute().getCityFrom(),
                schedule.getBusRoute().getCityTo(),
                schedule.getJourneyDate(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getTicketPrice(),
                schedule.getDiscount(),
                schedule.getProcessingFee()
        );
    }
}
