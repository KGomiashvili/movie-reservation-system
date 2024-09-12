package com.ol.springtask.demo.repository;

import com.ol.springtask.demo.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByMovieIdAndCinemaId(Integer movieId, Integer cinemaId);
    @Query("SELECT s FROM Schedule s WHERE s.cinema.id = :cinemaId")
    List<Schedule> findByCinemaId(@Param("cinemaId") Integer cinemaId);

    //Filtering by startDate, price, cinemaName, cinemaAddress, movieName, movieDescription
    @Query("SELECT s FROM Schedule s " +
            "WHERE (:startDate IS NULL OR s.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR s.endDate <= :endDate) " +
            "AND (:startPrice IS NULL OR s.price >= :startPrice) " +
            "AND (:endPrice IS NULL OR s.price <= :endPrice)")
    List<Schedule> findSchedulesWithFilters(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            @Param("startPrice") Double startPrice,
                                            @Param("endPrice") Double endPrice);

}