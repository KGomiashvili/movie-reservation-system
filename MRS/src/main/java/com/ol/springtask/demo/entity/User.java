package com.ol.springtask.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;


    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference("user-occupiedSeats")
    private List<OccupiedSeat> occupiedSeats;


    public User() {
    }

    public User(String username, String password, Role role, List<OccupiedSeat> occupiedSeats) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.occupiedSeats = occupiedSeats;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder occupiedSeatsIds = new StringBuilder();
        if (occupiedSeats != null) {
            for (OccupiedSeat occupiedSeat : occupiedSeats) {
                occupiedSeatsIds.append(occupiedSeat.getId()).append(", ");
            }
            if (occupiedSeatsIds.length() > 0) {
                occupiedSeatsIds.setLength(occupiedSeatsIds.length() - 2);  // Remove the last comma and space
            }
        }

        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", occupiedSeatsIds=[" + occupiedSeatsIds.toString() + "]" +
                '}';
    }

}
