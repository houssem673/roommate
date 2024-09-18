package roommate.web.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import roommate.application.service.BlockingService;
import roommate.domain.model.Booking;
import roommate.domain.model.Workspace;
import roommate.application.service.BookingService;
import roommate.application.service.WorkspaceService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class BookingAdministrationController {
    private final BookingService bookingService;
    private final WorkspaceService workspaceService;
    private final BlockingService blockingService;

    public BookingAdministrationController(BookingService bookingService, WorkspaceService workspaceService, BlockingService blockingService) {
        this.bookingService = bookingService;
        this.workspaceService = workspaceService;
        this.blockingService = blockingService;
    }

    @GetMapping("/admin/view-bookings") // admin only
    public String viewBookingsPage(Model model) {
        List<Booking> bookings = bookingService.allBookings();
        model.addAttribute("bookings", bookings);
        return "Admin/BookingAdministration";
    }
    @GetMapping("/admin/edit-booking/{bookingId}") // admin only
    public String editBookingPage(Model model, @PathVariable("bookingId") Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        List<Workspace> workspaces = workspaceService.findAllWorkspaces();
        model.addAttribute("workspaces", workspaces);
        return "Admin/EditBooking";
    }

    //KÖNNTE - muss nicht
    @PostMapping("/admin/edit-booking/{bookingId}") // admin only
    public String submitChangesBookingPage(@PathVariable("bookingId") Long bookingId, LocalDate date, LocalTime start, LocalTime end, String workspaceName) {
        Workspace desiredWorkspace = workspaceService.findWorkspaceByName(workspaceName);
        Long workspaceId = desiredWorkspace.getId();
        int updatedBooking = bookingService.updateBooking(bookingId, date, start, end, workspaceId);
        System.out.println(updatedBooking);
        if(updatedBooking == 0){
            return "redirect:/admin";
        }
        return "redirect:/admin/view-bookings";
    }

    //Exception oder Validation
    @PostMapping("/admin/delete-booking/{id}")
    public String deleteBooking(@PathVariable("id") Long id) {
        Booking booking = bookingService.getBookingById(id);
        bookingService.deleteBooking(booking);
        return "redirect:/admin/view-bookings";

    }


    @GetMapping("/admin/workspaces/block-timeframe")
    public String showBlockTimeframeFormPage(Model model){
        List<Workspace> workspaces = workspaceService.findAllWorkspaces();
        model.addAttribute("workspaces", workspaces);
        return "Admin/BlockWorkspace";
    }

    //pessimistisches Locking - weil Multilayered Transaction
    @PostMapping("/admin/workspaces/block-workspace")
    public String blockWorkspace(LocalDate date,
                                 LocalTime start,
                                 LocalTime end,
                                 String workspaceName,
                                 OAuth2AuthenticationToken token){
        String username = token.getPrincipal().getAttribute("login");
        blockingService.blockWorkspaceForTimeframe(date,start,end,workspaceName,username);
        return "redirect:/admin/view-bookings";
    }

}
