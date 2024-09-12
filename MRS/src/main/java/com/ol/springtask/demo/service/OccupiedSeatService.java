package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.OccupiedSeat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OccupiedSeatService {
    OccupiedSeat getOccupiedSeatById(Integer id);
    List<OccupiedSeat> getAllOccupiedSeats();
    OccupiedSeat save(OccupiedSeat occupiedSeat);
    void deleteOccupiedSeat(Integer id);

    OccupiedSeat bookTickets(Integer scheduleId, int seatsCount);

    OccupiedSeat confirmTicketPurchase(Integer occupiedSeatId);
}
