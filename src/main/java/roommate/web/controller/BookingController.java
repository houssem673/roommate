package roommate.web.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import roommate.application.service.BookingService;
import roommate.domain.model.Booking;
import roommate.web.request_entities.BookingForm;

import java.util.List;
@Controller
public class BookingController {
    /*
        - addBooking
        - viewBooking for User
     */
    private final BookingService bookingService ;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookinguser")
    public String showAllBookingForUser(Model model, OAuth2AuthenticationToken token) {
        String username = token.getPrincipal().getAttribute("login");
        List<Booking> allBookings = bookingService.findAllBookingsByUser(username);
        model.addAttribute("username",username);
        model.addAttribute("bookings",allBookings);

        return "Booking/view";
    }

    @GetMapping("/bookingconfirmation")
    public String workspace(Model model, BookingForm bookingForm) {
        model.addAttribute("date", bookingForm.getDate());
        model.addAttribute("start_time", bookingForm.getStart());
        model.addAttribute("end_time", bookingForm.getEnd());
        model.addAttribute("workspace_id", bookingForm.getWorkspaceId());

        return "Menu/bookingconfirmation";
    }

    //pessimistisch locken
    @PostMapping("/bookingconfirmation")
    public String bookWorkspace(BookingForm bookingForm, OAuth2AuthenticationToken token) {
        String username = token.getPrincipal().getAttribute("login");
        Booking newBooking = new Booking(bookingForm.getDate(), bookingForm.getStart(), bookingForm.getEnd(), bookingForm.getWorkspaceId(), username);
        bookingService.saveBooking(newBooking);
        return "redirect:/bookinguser";
    }

}
