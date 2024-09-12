package com.ol.springtask.demo.rest;

import com.ol.springtask.demo.entity.OccupiedSeat;
import com.ol.springtask.demo.service.OccupiedSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookingController {
    OccupiedSeatService occupiedSeatService;

    @Autowired
    public BookingController(OccupiedSeatService occupiedSeatService) {
        this.occupiedSeatService = occupiedSeatService;
    }

    @PostMapping()
    public OccupiedSeat bookTickets(@RequestParam Integer scheduleId,
                                    @RequestParam int seatsCount) {
        return occupiedSeatService.bookTickets(scheduleId, seatsCount);
    }

    //Confirm the purchase of reserved tickets
    @PostMapping("/confirm")
    public OccupiedSeat confirmBooking(@RequestParam Integer occupiedSeatId) {
        return occupiedSeatService.confirmTicketPurchase(occupiedSeatId);
    }

}
