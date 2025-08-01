package net.busbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class BusReservationBackend {

    public static void main(String[] args) {
        System.out.println("✅ ENV PORT: " + System.getenv("PORT"));
        SpringApplication.run(BusReservationBackend.class, args);
    }


}