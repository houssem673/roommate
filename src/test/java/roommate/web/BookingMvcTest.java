package roommate.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import roommate.application.service.BookingService;
import roommate.domain.model.Booking;
import roommate.security.helper.WithMockOAuth2User;
import roommate.web.controller.BookingController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookingController.class)
public class BookingMvcTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    BookingService bookingService;

    @Test
    @DisplayName("Get Request for /bookinguser complete test")
    @WithMockOAuth2User(login = "ozan")
    void test_1() throws Exception {
        List<Booking> bookings = List.of(new Booking(1L, LocalDate.of(2024, 12, 5), LocalTime.of(10, 0), LocalTime.of(15, 0), 2, "ozan"));
        when(bookingService.findAllBookingsByUser("ozan")).thenReturn(
                bookings);
        mvc.perform(get("/bookinguser"))
                .andExpect(status().isOk())
                .andExpect(view().name("Booking/view"))
                .andExpect(model().attribute("bookings",bookings))
                .andExpect(model().attribute("username","ozan"));
    }

    @Test
    @DisplayName("Get Request for /bookingconfirmation returns OK")
    @WithMockOAuth2User(login = "ozan")
    void test_2() throws Exception {
        mvc.perform(get("/bookingconfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("Menu/bookingconfirmation"));
    }
    @Test
    @DisplayName("Post Request for /bookingconfirmation returns OK")
    @WithMockOAuth2User(login = "ozanozden")
    void test_3() throws Exception {
        mvc.perform(post("/bookingconfirmation")
                        .with(csrf())
                        .param("date", "2024-03-01")
                        .param("start", "10:00")
                        .param("end", "12:00")
                        .param("workspaceId", "2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bookinguser"));
        verify(bookingService,times(1)).saveBooking(any());
    }



}
