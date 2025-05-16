package net.busbackend.controller;

import net.busbackend.entites.Bus;
import net.busbackend.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/bus")
public class BusController {
    @Autowired
    private BusService busService;

    //now create two endpoints

    @PostMapping("/add")
    public String addBus(@RequestBody Bus bus){
        busService.addBus(bus);
        return "Bus Saved Successfully";
    }

    @GetMapping("/all")
    public List<Bus> getAllBus(){
        return busService.getAllBus();
    }

}

