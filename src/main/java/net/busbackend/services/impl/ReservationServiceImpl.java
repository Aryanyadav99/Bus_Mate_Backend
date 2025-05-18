package net.busbackend.services.impl;
import net.busbackend.entites.BusSchedule;
import net.busbackend.entites.Customer;
import net.busbackend.entites.Reservation;
import net.busbackend.models.ReservationApiException;
import net.busbackend.repos.BusScheduleRepository;
import net.busbackend.repos.CustomerRepository;
import net.busbackend.repos.ReservationRepository;
import net.busbackend.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BusScheduleRepository busScheduleRepository;
    @Override
    public Reservation addReservation(Reservation reservation) {
        final Customer customer;
        final boolean doesCustomerExist=customerRepository.existsByMobileOrEmail(
                reservation.getCustomer().getMobile(),
                reservation.getCustomer().getEmail()
        ) ;
        if(doesCustomerExist){
            customer=customerRepository.findByMobileOrEmail(
                    reservation.getCustomer().getMobile(),
                    reservation.getCustomer().getEmail()
            ).orElseThrow(() -> new RuntimeException("Customer not found"));
        }
        else{
            customer=customerRepository.save(reservation.getCustomer());
        }
        reservation.setCustomer(customer);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationByScheduleAndDepartureDate(Long scheduleId, String departureDate) {
        BusSchedule busSchedule=busScheduleRepository
                .findById(scheduleId)
                .orElseThrow(()-> new ReservationApiException(HttpStatus.BAD_REQUEST,"Schedule Not found"));
        return reservationRepository
                .findByBusScheduleAndDepartureDate(busSchedule,departureDate)
                .orElseThrow(()->new ReservationApiException(HttpStatus.BAD_REQUEST,"Reservation Not Found"));
    }

    @Override
    public List<Reservation> getReservationByMobile(String mobile) {
        final Customer customer=customerRepository
                .findByMobile(mobile)
                .orElseThrow(()->new ReservationApiException(HttpStatus.BAD_REQUEST,"No record found"));
        return reservationRepository.findByCustomer(customer).orElseThrow(()->new ReservationApiException(HttpStatus.BAD_REQUEST,"No Reservation Found"));
    }
}
