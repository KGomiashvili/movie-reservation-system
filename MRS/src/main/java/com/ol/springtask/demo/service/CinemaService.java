package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Cinema;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CinemaService {
    Cinema getCinemaById(Integer id);
    List<Cinema> getAllCinemas();
    Cinema save(Cinema cinema);
    Cinema deleteCinema(Integer id);

    List<Cinema> getFilteredCinemas(String name, String address);
}