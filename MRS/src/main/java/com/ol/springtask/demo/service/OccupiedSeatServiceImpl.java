package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Schedule;
import com.ol.springtask.demo.entity.SeatStatus;
import com.ol.springtask.demo.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import com.ol.springtask.demo.entity.User;
import com.ol.springtask.demo.repository.UserRepository;
import com.ol.springtask.demo.entity.OccupiedSeat;
import com.ol.springtask.demo.repository.OccupiedSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ol.springtask.demo.entity.Role.ROLE_ADMIN;

@Service
public class OccupiedSeatServiceImpl implements OccupiedSeatService {

    private final OccupiedSeatRepository occupiedSeatRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public OccupiedSeatServiceImpl(OccupiedSeatRepository occupiedSeatRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.occupiedSeatRepository = occupiedSeatRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public OccupiedSeat getOccupiedSeatById(Integer id) {
        Optional<OccupiedSeat> result = occupiedSeatRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Did not find Occupied Seat id: " + id));
    }

    @Override
    public List<OccupiedSeat> getAllOccupiedSeats() {
        //regular users can only view their seats
        User currentUser = getCurrentUser();
        if (isAdmin(currentUser)) {
            return occupiedSeatRepository.findAll();
        } else {
            return occupiedSeatRepository.findByUserId(currentUser.getId());
        }
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            return userRepository.findByUsername(username);
        }

        throw new IllegalStateException("Unexpected user principal type");
    }

    private boolean isAdmin(User user) {
        return user.getRole().equals(ROLE_ADMIN);
    }

    @Override
    @Transactional
    public OccupiedSeat save(OccupiedSeat occupiedSeat) {
        return occupiedSeatRepository.save(occupiedSeat);
    }

    @Override
    @Transactional
    public void deleteOccupiedSeat(Integer id) {
        occupiedSeatRepository.deleteById(id);
    }

    @Transactional
    @Override
    public OccupiedSeat bookTickets(Integer scheduleId, int numberOfTickets) {
        //Get the user
        User currentUser = getCurrentUser();


        // Get the schedule
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));

        // Check if the booking is not within 30 minutes before start
        LocalDateTime now = LocalDateTime.now();
        if (schedule.getStartDate().minusMinutes(30).isBefore(now)) {
            throw new IllegalStateException("Cannot book tickets within 30 minutes before the movie starts.");
        }


        // Check if there are enough remaining seats
        int remainingSeats = schedule.getRemainingSeats();
        if (remainingSeats < numberOfTickets) {
            throw new IllegalStateException("Not enough remaining seats available.");
        }

        // Update remainingSeats
        schedule.setRemainingSeats(remainingSeats - numberOfTickets);
        scheduleRepository.save(schedule);

        // Create and save the new occupied seat
        OccupiedSeat occupiedSeat = new OccupiedSeat();
        occupiedSeat.setSchedule(schedule);
        occupiedSeat.setSeatsCount(numberOfTickets);
        occupiedSeat.setDate(now);
        occupiedSeat.setUser(currentUser);
        occupiedSeat.setSeatStatus(SeatStatus.RESERVED);
        occupiedSeat = occupiedSeatRepository.save(occupiedSeat);

        return occupiedSeat;
    }


    @Override
    @Transactional
    public OccupiedSeat confirmTicketPurchase(Integer occupiedSeatId) {
        OccupiedSeat occupiedSeat = occupiedSeatRepository.findById(occupiedSeatId)
                .orElseThrow(() -> new EntityNotFoundException("Occupied seat not found"));

        // Update seat status to SOLD
        if (occupiedSeat.getSeatStatus() == SeatStatus.RESERVED) {
            occupiedSeat.setSeatStatus(SeatStatus.SOLD);
            return occupiedSeatRepository.save(occupiedSeat);
        } else {
            throw new IllegalStateException("Tickets already sold.");
        }
    }

    // Cancel reservations 30 minutes before movie starts
    @Scheduled(fixedRate = 60000)  // Runs every minute
    @Transactional
    public void cancelExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        // Find all "RESERVED" seats that are within the next 30 minutes
        List<OccupiedSeat> expiredReservations = occupiedSeatRepository
                .findExpiredReservations(now.plusMinutes(30), SeatStatus.RESERVED);

        for (OccupiedSeat seat : expiredReservations) {
            //add to remaining seats
            seat.getSchedule().setRemainingSeats(seat.getSchedule().getRemainingSeats()+seat.getSeatsCount());
            occupiedSeatRepository.delete(seat);
        }



    }


}
