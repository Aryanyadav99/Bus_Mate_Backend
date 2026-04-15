package net.busbackend.services;
import java.util.*;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;

import jakarta.validation.Valid;

public interface BusService {
    BusResponseDto addBus(BusRequestDto busRequestDto);
    List<BusResponseDto> getAllBus();

    BusResponseDto getBusById(Long id);

    void deleteBus(Long id);

    BusResponseDto updateBus(Long id, @Valid BusRequestDto requestDto);
}
