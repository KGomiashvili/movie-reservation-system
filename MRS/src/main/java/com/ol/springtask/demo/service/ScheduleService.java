package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule getScheduleById(Integer id);
    List<Schedule> getAllSchedules();
    Schedule save(Schedule schedule);
    void deleteSchedule(Integer id);

    List<Schedule> getSchedulesByMovieAndCinema(Integer movieId, Integer cinemaId);

    List<Schedule> getFilteredSchedules(
            LocalDate startDate, LocalDate endDate,
            Double startPrice, Double endPrice);
}
