package net.busbackend.services;
import java.util.*;
import net.busbackend.entites.Bus;

public interface BusService {
    Bus addBus(Bus bus);
    List<Bus> getAllBus();

}
