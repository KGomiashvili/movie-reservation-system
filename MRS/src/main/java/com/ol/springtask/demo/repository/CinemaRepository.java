package com.ol.springtask.demo.repository;

import com.ol.springtask.demo.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    @Query("SELECT c FROM Cinema c WHERE " +
            "(:name IS NULL OR c.name LIKE %:name%) AND " +
            "(:address IS NULL OR c.address LIKE %:address%)")
    List<Cinema> findCinemasWithFilters(@Param("name") String name, @Param("address") String address);

}
