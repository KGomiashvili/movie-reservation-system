package com.ol.springtask.demo.rest;

import com.ol.springtask.demo.entity.Schedule;
import com.ol.springtask.demo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public List<Schedule> getSchedules(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Double startPrice,
            @RequestParam(required = false) Double endPrice
    ) {
        return scheduleService.getFilteredSchedules(startDate, endDate, startPrice, endPrice);
    }


    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Integer id) {
        return scheduleService.getScheduleById(id);
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.save(schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Integer id, @RequestBody Schedule schedule) {
        schedule.setId(id);
        return scheduleService.save(schedule);
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return "Deleted schedule id: " + id;
    }

    @GetMapping("/search")
    public List<Schedule> getSchedulesByMovieAndCinema(
            @RequestParam Integer movieId,
            @RequestParam Integer cinemaId) {
        return scheduleService.getSchedulesByMovieAndCinema(movieId, cinemaId);
    }

}
