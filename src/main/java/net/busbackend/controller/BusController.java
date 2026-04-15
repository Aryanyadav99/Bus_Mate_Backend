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

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    @Autowired
    private BusService busService;

    //now create two endpoints
    // used to create bus in db
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

}

