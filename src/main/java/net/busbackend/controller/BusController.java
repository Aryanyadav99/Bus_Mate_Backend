package net.busbackend.controller;

import net.busbackend.entites.Bus;
import net.busbackend.models.ResponseModel;
import net.busbackend.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/bus")
public class BusController {
    @Autowired
    private BusService busService;

    //now create two endpoints

    @PostMapping("/add")
    public ResponseModel<Bus> addBus(@RequestBody Bus bus){
        //instead of returning only String we got a repose model
        final Bus savedbus=busService.addBus(bus);
        //this saved bus came from service and then jpa send the bus table which also consist the id so we use it instead if bus
        return new ResponseModel<>(HttpStatus.OK.value(),"Bus Saved",savedbus);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bus>> getAllBus(){
        //we can only use list bus there,but that look more professional means we are returning the response
        return ResponseEntity.ok(busService.getAllBus());
    }

}

