package net.busbackend.services;

import net.busbackend.entites.BusSchedule;

import java.util.List;

public interface BusScheduleService  {
    BusSchedule addSchedule(BusSchedule busSchedule);
    List<BusSchedule> getAllBusSchedules();
    List<BusSchedule>getSchedulesByRoute(String routeName);

}
