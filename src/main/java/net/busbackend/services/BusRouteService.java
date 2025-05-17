package net.busbackend.services;
import java.util.*;
import net.busbackend.entites.BusRoute;
import org.springframework.stereotype.Service;


public interface BusRouteService {
    BusRoute addRoute(BusRoute busRoute);
    List<BusRoute> getAllBusRoutes();

    BusRoute getRouteByRouteName(String routeName);
    BusRoute getRouteByCityFromAndCityTo(String cityFrom , String cityTo);
}
