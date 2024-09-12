package com.ol.springtask.demo.rest;


import com.ol.springtask.demo.entity.Cinema;
import com.ol.springtask.demo.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public List<Cinema> getAllCinemas(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address) {
        return cinemaService.getFilteredCinemas(name, address);
    }

    @GetMapping("/{id}")
    public Cinema getCinemaById(@PathVariable Integer id) {
        return  cinemaService.getCinemaById(id);}

    @PostMapping
    public Cinema createCinema(@RequestBody Cinema cinema) {
        return cinemaService.save(cinema);
    }

    @PutMapping("/{id}")
    public Cinema updateCinema(@PathVariable Integer id, @RequestBody Cinema cinema) {
        cinema.setId(id);
        return cinemaService.save(cinema);
    }

    @DeleteMapping("/{id}")
    public String deleteCinema(@PathVariable Integer id) {
        Cinema theCinema = cinemaService.getCinemaById(id);

        if (theCinema == null) throw new RuntimeException("Cinema id not found: " + id);
        cinemaService.deleteCinema(id);

        return "Deleted cinema id: " + id;
    }
}