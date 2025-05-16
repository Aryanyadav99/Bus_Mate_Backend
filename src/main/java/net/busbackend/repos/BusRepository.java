package net.busbackend.repos;

import net.busbackend.entites.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus,Long> {
    //in this class we will access our database through jpa
    // all crud operation done through jpa

}
