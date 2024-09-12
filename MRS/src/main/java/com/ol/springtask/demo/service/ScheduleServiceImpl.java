package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Schedule;
import com.ol.springtask.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule getScheduleById(Integer id) {
        Optional<Schedule> result = scheduleRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Did not find Schedule id: " + id));
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> getSchedulesByMovieAndCinema(Integer movieId, Integer cinemaId) {
        return scheduleRepository.findByMovieIdAndCinemaId(movieId, cinemaId);
    }

    @Override
    public List<Schedule> getFilteredSchedules(
            LocalDate startDate,
            LocalDate endDate,
            Double startPrice,
            Double endPrice) {

        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;

        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;


        System.out.println("Start Price: " + startPrice + ", End Price: " + endPrice);

        return scheduleRepository.findSchedulesWithFilters(startDateTime, endDateTime, startPrice, endPrice);

    }
}

