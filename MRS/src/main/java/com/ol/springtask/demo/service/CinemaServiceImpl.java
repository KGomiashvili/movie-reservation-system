package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Cinema;
import com.ol.springtask.demo.entity.Schedule;
import com.ol.springtask.demo.repository.CinemaRepository;
import com.ol.springtask.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {
    private CinemaRepository cinemaRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public CinemaServiceImpl(CinemaRepository cinemaRepository, ScheduleRepository scheduleRepository) {
        this.cinemaRepository = cinemaRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Cinema getCinemaById(Integer id) {
        Optional<Cinema> result = cinemaRepository.findById(id);
        Cinema theCinema = null;

        if (result.isPresent()) theCinema = result.get();
        else throw new RuntimeException("Did not find Cinema id: " + id);
        return theCinema;
    }

    @Override
    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    @Transactional
    public Cinema save(Cinema cinema) {
        //Catch integrity violation if name/address combo isn't unique
        try {
            return cinemaRepository.save(cinema);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Cinema with this name and address already exists.");
        }
    }



    @Override
    @Transactional
    public Cinema deleteCinema(Integer id) {
        // First, delete related schedules
        List<Schedule> schedules = scheduleRepository.findByCinemaId(id);
        for (Schedule schedule : schedules) {
            scheduleRepository.delete(schedule);
        }
        cinemaRepository.deleteById(id);
        return null;
    }

    @Override
    public List<Cinema> getFilteredCinemas(String name, String address) {
        return cinemaRepository.findCinemasWithFilters(name, address);
    }
}
