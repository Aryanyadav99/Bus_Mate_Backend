package net.busbackend.services.impl;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;
import net.busbackend.entites.Bus;
import net.busbackend.repos.BusRepository;
import net.busbackend.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public BusResponseDto addBus(BusRequestDto busRequestDto) {
        // bus from dto
        Bus bus=mapToEntity(busRequestDto);
        // save bus to db
        Bus savedBus=busRepository.save(bus);
        // bus to dto
        return mapToDto(savedBus);
    }

    @Override
    public List<Bus> getAllBus() {
        return busRepository.findAll();
    }

    private Bus mapToEntity(BusRequestDto dto) {
        Bus bus = new Bus();
        bus.setBusName(dto.getBusName());
        bus.setBusType(dto.getBusType());
        bus.setTotalSeat(dto.getTotalSeats());
        bus.setBusNumber(dto.getBusNumber());
        return bus;
    }
    private BusResponseDto mapToDto(Bus bus) {
        return new BusResponseDto(
                bus.getBusId(),
                bus.getBusName(),
                bus.getBusType(),
                bus.getTotalSeat(),
                bus.getBusNumber()
        );
    }
}
