package net.busbackend.repos;
import net.busbackend.entites.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
    Optional<BusRoute> findByRouteName(String routeName);
    Optional<BusRoute>  findByCityFromAndCityTo(String cityFrom,String cityTo);
}
