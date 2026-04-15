package net.busbackend.services;
import java.util.*;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;
import net.busbackend.entites.Bus;

public interface BusService {
    BusResponseDto addBus(BusRequestDto busRequestDto);
    List<Bus> getAllBus();

}
