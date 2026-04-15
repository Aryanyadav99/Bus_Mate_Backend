package net.busbackend.controller;

import net.busbackend.DTO.BusRequestDto;
import net.busbackend.DTO.BusResponseDto;
import net.busbackend.entites.Bus;
import net.busbackend.models.ResponseModel;
import net.busbackend.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    @Autowired
    private BusService busService;

    //now create two endpoints
    // used to create bus in db
    // bus can be added only by admin okay so just make sure that only admin can access this endpoint
    @PostMapping
    public ResponseEntity<ResponseModel<BusResponseDto>> addBus(@Valid @RequestBody BusRequestDto busRequestDto){
        //instead of returning only String we got a repose model

        final BusResponseDto savedbus=busService.addBus(busRequestDto);
        //this saved bus came from service and then jpa send the bus table which also consist the id so we use it instead if bus
        ResponseModel<BusResponseDto> response=  new ResponseModel<>(HttpStatus.CREATED.value(),"Bus Saved",savedbus);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BusResponseDto>> getAllBus() {

        List<BusResponseDto> buses = busService.getAllBus();

        return ResponseEntity.ok(buses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusResponseDto> getBusById(@PathVariable Long id) {

        BusResponseDto bus = busService.getBusById(id);

        return ResponseEntity.ok(bus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {

        busService.deleteBus(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<BusResponseDto> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusRequestDto requestDto) {

        BusResponseDto updatedBus = busService.updateBus(id, requestDto);

        return ResponseEntity.ok(updatedBus);
    }

}

