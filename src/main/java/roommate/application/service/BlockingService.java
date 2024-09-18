package roommate.application.service;

import org.springframework.stereotype.Service;
import roommate.domain.model.Booking;
import roommate.domain.model.Workspace;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class BlockingService {
    private final BookingService bookingService;
    private final WorkspaceService workspaceService;

    public BlockingService(BookingService bookingService, WorkspaceService workspaceService) {
        this.bookingService = bookingService;

        this.workspaceService = workspaceService;
    }

    public void blockWorkspaceForTimeframe(LocalDate date,
                                      LocalTime start,
                                      LocalTime end,
                                      String workspaceName, String username){
        Workspace workspace = workspaceService.findWorkspaceByName(workspaceName);
        long converter = workspace.getId();
        int workspaceId = (int) converter;
        bookingService.deleteBookingForBlocking(workspace.getId(), date,start,end);
        Booking booking = new Booking(date, start, end, workspaceId, username);
        bookingService.saveBooking(booking);
    }
}
