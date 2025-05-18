package net.busbackend.services.impl;

import net.busbackend.entites.BusRoute;
import net.busbackend.entites.BusSchedule;
import net.busbackend.models.ReservationApiException;
import net.busbackend.repos.BusRouteRepository;
import net.busbackend.repos.BusScheduleRepository;
import net.busbackend.services.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusScheduleServiceImpl implements BusScheduleService {
    @Autowired
    private BusScheduleRepository busScheduleRepository;
    @Autowired
    private BusRouteRepository busRouteRepository;


    @Override
    public BusSchedule addSchedule(BusSchedule busSchedule) throws  ReservationApiException {
        if(busScheduleRepository.existsByBusAndBusRouteAndDepartureTime(busSchedule.getBus(),busSchedule.getBusRoute(),busSchedule.getDepartureTime())){
            throw new ReservationApiException(HttpStatus.CONFLICT,"Duplicate Schedule");
        }
        return busScheduleRepository.save(busSchedule);
    }

    @Override
    public List<BusSchedule> getAllBusSchedules() {
        return busScheduleRepository.findAll();
    }

    @Override
    public List<BusSchedule> getSchedulesByRoute(String routeName) {
        final BusRoute busRoute=busRouteRepository.findByRouteName(routeName).orElseThrow(()-> new ReservationApiException(HttpStatus.BAD_REQUEST,"Not found"));
        return busScheduleRepository.findByBusRoute(busRoute).orElseThrow(()-> new ReservationApiException(HttpStatus.BAD_REQUEST,"Not found"));
    }
}
