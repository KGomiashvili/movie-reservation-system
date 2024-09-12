package com.ol.springtask.demo.rest;

import com.ol.springtask.demo.entity.OccupiedSeat;
import com.ol.springtask.demo.service.OccupiedSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/occupied-seats")
public class OccupiedSeatController {

    private final OccupiedSeatService occupiedSeatService;

    @Autowired
    public OccupiedSeatController(OccupiedSeatService occupiedSeatService) {
        this.occupiedSeatService = occupiedSeatService;
    }

    @GetMapping
    public List<OccupiedSeat> getAllOccupiedSeats() {
        return occupiedSeatService.getAllOccupiedSeats();
    }

    @GetMapping("/{id}")
    public OccupiedSeat getOccupiedSeatById(@PathVariable Integer id) {
        return occupiedSeatService.getOccupiedSeatById(id);
    }

    @PostMapping
    public OccupiedSeat createOccupiedSeat(@RequestBody OccupiedSeat occupiedSeat) {
        return occupiedSeatService.save(occupiedSeat);
    }

    @PutMapping("/{id}")
    public OccupiedSeat updateOccupiedSeat(@PathVariable Integer id, @RequestBody OccupiedSeat occupiedSeat) {
        occupiedSeat.setId(id);
        return occupiedSeatService.save(occupiedSeat);
    }

    @DeleteMapping("/{id}")
    public String deleteOccupiedSeat(@PathVariable Integer id) {
        occupiedSeatService.deleteOccupiedSeat(id);
        return "Deleted occupied seat id: " + id;
    }
}
