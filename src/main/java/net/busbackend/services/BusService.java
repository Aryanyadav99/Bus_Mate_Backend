package net.busbackend.services;
import java.util.*;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;

public interface BusService {
    BusResponseDto addBus(BusRequestDto busRequestDto);
    List<BusResponseDto> getAllBus();

}
