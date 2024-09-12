package com.ol.springtask.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference("movie-schedules")
    private Movie movie;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "remaining_seats")
    private Integer remainingSeats;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference("schedule-occupiedSeats")
    private List<OccupiedSeat> occupiedSeats;

    public Schedule() {
    }


    public Schedule(Cinema cinema, Movie movie, LocalDateTime startDate, LocalDateTime endDate, Double price, Integer totalSeats, Integer remainingSeats, List<OccupiedSeat> occupiedSeats) {
        this.cinema = cinema;
        this.movie = movie;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
        this.occupiedSeats = occupiedSeats;
    }

    public Schedule(Cinema cinema, Movie movie, LocalDateTime startDate, LocalDateTime endDate, Double price, Integer totalSeats, List<OccupiedSeat> occupiedSeats) {
        this.cinema = cinema;
        this.movie = movie;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.totalSeats = totalSeats;
        this.remainingSeats = totalSeats;
        this.occupiedSeats = occupiedSeats;
    }
    // Getters and Setters...

    public List<OccupiedSeat> getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(List<OccupiedSeat> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(Integer remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    @Override
    public String toString() {
        StringBuilder occupiedSeatsIds = new StringBuilder();
        if (occupiedSeats != null) {
            for (OccupiedSeat occupiedSeat : occupiedSeats) {
                occupiedSeatsIds.append(occupiedSeat.getId()).append(", ");
            }
            if (occupiedSeatsIds.length() > 0) {
                occupiedSeatsIds.setLength(occupiedSeatsIds.length() - 2);
            }
        }

        return "Schedule{" +
                "id=" + id +
                ", cinemaId=" + (cinema != null ? cinema.getId() : "null") +
                ", movieId=" + (movie != null ? movie.getId() : "null") +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", totalSeats=" + totalSeats +
                ", occupiedSeatsIds=[" + occupiedSeatsIds.toString() + "]" +
                '}';
    }
}
