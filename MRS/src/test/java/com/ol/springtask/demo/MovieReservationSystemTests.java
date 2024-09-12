package com.ol.springtask.demo;

import com.ol.springtask.demo.entity.*;
import com.ol.springtask.demo.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieReservationSystemTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private CinemaService cinemaService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private OccupiedSeatService occupiedSeatService;

	// Guest Access Tests

	@Test
	public void testViewCinemasAsGuest() throws Exception {
		mockMvc.perform(get("/api/cinemas"))
				.andExpect(status().isOk());
	}

	@Test
	public void testViewMoviesAsGuest() throws Exception {
		mockMvc.perform(get("/api/movies"))
				.andExpect(status().isOk());
	}

	@Test
	public void testViewSchedulesAsGuest() throws Exception {
		mockMvc.perform(get("/api/schedules"))
				.andExpect(status().isOk());
	}

	// User Booking Tests

	@Test
	@WithMockUser(roles = "USER")
	public void testBookTicketsAsUser() throws Exception {
		mockMvc.perform(post("/api/book")
						.param("scheduleId", "1")
						.param("seatsCount", "2"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testViewOccupiedSeatsAsUser() throws Exception {
		mockMvc.perform(get("/api/occupied-seats"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testConfirmBookingAsUser() throws Exception {
		mockMvc.perform(post("/api/book/confirm")
						.param("occupiedSeatId", "1"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testBookingTimeRestriction() throws Exception {
		// Test for booking restriction (within 30 minutes of movie start)
		mockMvc.perform(post("/api/book")
						.param("scheduleId", "1")
						.param("seatsCount", "2"))
				.andExpect(status().isBadRequest());
	}

	// Admin Management Tests

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCreateUserAsAdmin() throws Exception {
		String newUser = "{ \"username\": \"newuser\", \"password\": \"password\", \"role\": \"ROLE_USER\" }";
		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newUser))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteUserAsAdmin() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", 1))
				.andExpect(status().isOk());
	}

	// Cinema Management Tests

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCreateCinemaAsAdmin() throws Exception {
		String newCinema = "{ \"name\": \"Cinema X\", \"address\": \"123 Street\" }";
		mockMvc.perform(post("/api/cinemas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newCinema))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteCinemaAsAdmin() throws Exception {
		mockMvc.perform(delete("/api/cinemas/{id}", 1))
				.andExpect(status().isOk());
	}

	// Movie Management Tests

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCreateMovieAsAdmin() throws Exception {
		String newMovie = "{ \"name\": \"Movie X\", \"description\": \"A great movie\" }";
		mockMvc.perform(post("/api/movies")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newMovie))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testUpdateMovieAsAdmin() throws Exception {
		String updatedMovie = "{ \"name\": \"Updated Movie\", \"description\": \"Updated description\" }";
		mockMvc.perform(put("/api/movies/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updatedMovie))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteMovieAsAdmin() throws Exception {
		mockMvc.perform(delete("/api/movies/{id}", 1))
				.andExpect(status().isOk());
	}

	// Schedule Management Tests


	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCreateScheduleAsAdmin() throws Exception {
		// Create schedule with the existing cinema and movie IDs
		String newSchedule = String.format(
				"{ \"cinemaId\": 1, \"movieId\": 2, \"startDate\": \"2024-12-25T14:00:00\", \"endDate\": \"2024-12-25T16:00:00\", \"price\": 12.50, \"totalSeats\": 100, \"remainingSeats\": 100 }"
		);

		mockMvc.perform(post("/api/schedules")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newSchedule))
				.andExpect(status().isOk());
	}



	@Test
	@WithMockUser(roles = "ADMIN")
	public void testUpdateScheduleAsAdmin() throws Exception {
		String updatedSchedule = "{ \"cinemaId\": 1, \"movieId\": 2, \"startDate\": \"2024-12-26T14:00:00\", \"endDate\": \"2024-12-26T16:00:00\", \"price\": 14.00, \"totalSeats\": 100, \"remainingSeats\": 100 }";
		mockMvc.perform(put("/api/schedules/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updatedSchedule))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteScheduleAsAdmin() throws Exception {
		mockMvc.perform(delete("/api/schedules/{id}", 1))
				.andExpect(status().isOk());
	}

	// Additional Tests for Filtering Schedules, Movies, and Cinemas

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testFilterSchedules() throws Exception {
		mockMvc.perform(get("/api/schedules")
						.param("startDate", LocalDate.of(2024, 12, 25).toString())
						.param("endDate", LocalDate.of(2024, 12, 31).toString())
						.param("startPrice", "10.00")
						.param("endPrice", "20.00"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testFilterMovies() throws Exception {
		mockMvc.perform(get("/api/movies")
						.param("name", "Movie X")
						.param("description", "great"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testFilterCinemas() throws Exception {
		mockMvc.perform(get("/api/cinemas")
						.param("name", "Cinema X")
						.param("address", "123 Street"))
				.andExpect(status().isOk());
	}

	// Reservation Cancellation Tests


}
