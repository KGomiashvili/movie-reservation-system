package com.ol.springtask.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "occupied_seat")
public class OccupiedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonBackReference("schedule-occupiedSeats")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-occupiedSeats")
    private User user;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status")
    private SeatStatus seatStatus;

    @Column(name = "seats_count")
    private Integer seatsCount;

    public OccupiedSeat() {
    }

    public OccupiedSeat(Schedule schedule, User user, LocalDateTime date, SeatStatus seatStatus, Integer seatsCount) {
        this.schedule = schedule;
        this.user = user;
        this.date = date;
        this.seatStatus = seatStatus;
        this.seatsCount = seatsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Integer getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(Integer seatsCount) {
        this.seatsCount = seatsCount;
    }

    @Override
    public String toString() {
        return "OccupiedSeat{" +
                "id=" + id +
                ", scheduleId=" + (schedule != null ? schedule.getId() : "null") +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", date=" + date +
                ", seatStatus=" + seatStatus +
                ", seatsCount=" + seatsCount +
                '}';
    }

}
