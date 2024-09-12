package com.ol.springtask.demo.repository;

import com.ol.springtask.demo.entity.OccupiedSeat;
import com.ol.springtask.demo.entity.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OccupiedSeatRepository extends JpaRepository<OccupiedSeat, Integer> {
    @Query("SELECT os FROM OccupiedSeat os WHERE os.schedule.id = :scheduleId AND os.seatStatus = :seatStatus")
    List<OccupiedSeat> findAvailableSeats(@Param("scheduleId") Integer scheduleId, @Param("seatStatus") SeatStatus seatStatus);

    @Query("SELECT COUNT(os) FROM OccupiedSeat os WHERE os.schedule.id = :scheduleId AND os.seatStatus = :seatStatus")
    long countByScheduleIdAndSeatStatus(@Param("scheduleId") Integer scheduleId, @Param("seatStatus") SeatStatus seatStatus);

    @Query("SELECT os FROM OccupiedSeat os WHERE os.user.id = :userId")
    List<OccupiedSeat> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT os FROM OccupiedSeat os WHERE os.schedule.startDate <= :time AND os.seatStatus = :seatStatus")
    List<OccupiedSeat> findExpiredReservations(@Param("time") LocalDateTime time, @Param("seatStatus") SeatStatus seatStatus);


}