package net.busbackend.services.impl;
import jakarta.transaction.Transactional;
import net.busbackend.DTO.ReservationRequestDto;
import net.busbackend.DTO.ReservationResponseDto;
import net.busbackend.entites.*;
import net.busbackend.repos.*;
import net.busbackend.services.ReservationService;
import net.busbackend.util.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Transactional

    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto dto) {

        //  1. Validate User (login required)
       User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  2. Fetch Schedule
        BusSchedule schedule = busScheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        int totalPrice = 0;
        List<Seat> bookedSeats = new ArrayList<>();
        for (Integer seatNo : dto.getSeatNumbers()) {

            String key = "seat_lock:" + dto.getScheduleId() + ":" + seatNo;

            String lockedBy = redisTemplate.opsForValue().get(key);

            //  FINAL LOCK VALIDATION
            if (lockedBy == null || !lockedBy.equals(dto.getSessionId())) {
                throw new RuntimeException("Seat lock expired or not owned: " + seatNo);
            }

            Seat seat = seatRepository
                    .findByScheduleAndSeatNumber(schedule, seatNo)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatNo));

            if (seat.getIsBooked()) {
                throw new RuntimeException("Seat already booked: " + seatNo);
            }

            //  mark booked
            seat.setIsBooked(true);
            seatRepository.save(seat); // @Version safe

            redisTemplate.delete(key);

            bookedSeats.add(seat);
            totalPrice += schedule.getTicketPrice();
        }

        // 3. Create Customer
        Customer customer = new Customer();
        customer.setCustomerName(dto.getPassengerName());
        customer.setEmail(dto.getPassengerEmail());
        customer.setMobile(dto.getPassengerMobile());

        customerRepository.save(customer);

        // 4. Create Reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCustomer(customer);
        reservation.setBusSchedule(schedule);
        reservation.setSeats(bookedSeats);
        reservation.setTotalSeatBooked(bookedSeats.size());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Reservation saved = reservationRepository.save(reservation);

        //  5. Return DTO
        return new ReservationResponseDto(
                saved.getReservationId(),
                saved.getSeats().stream()
                        .map(seat -> String.valueOf(seat.getSeatNumber()))
                        .collect(Collectors.joining(",")),
                saved.getTotalPrice(),
                saved.getStatus().name(),
                saved.getCreatedAt()
        );
    }
}