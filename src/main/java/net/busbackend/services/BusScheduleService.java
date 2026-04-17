package net.busbackend.services;

import net.busbackend.DTO.BusScheduleRequestDTO;
import net.busbackend.DTO.BusScheduleResponseDTO;
import net.busbackend.entites.BusSchedule;

import java.util.List;

public interface BusScheduleService  {
    BusScheduleResponseDTO addSchedule(BusScheduleRequestDTO busScheduleRequestDTO);
    List<BusSchedule> getAllBusSchedules();
    List<BusSchedule>getSchedulesByRoute(String routeName);

}
