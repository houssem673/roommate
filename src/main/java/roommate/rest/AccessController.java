package roommate.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import roommate.application.service.BookingService;
import roommate.application.service.WorkspaceService;
import roommate.domain.model.Booking;
import roommate.rest.AccessEntity.Access;
import roommate.rest.AccessEntity.Key;
import roommate.rest.AccessEntity.Room;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;



@RestController
public class AccessController {

    private final BookingService bookingService;
    private final WorkspaceService workspaceService;

    private  final AccessSync accessSync;
    public AccessController(BookingService bookingService, WorkspaceService workspaceService,AccessSync accessSync) {
        this.bookingService = bookingService;
        this.workspaceService = workspaceService;
        this.accessSync = accessSync;
    }


    @GetMapping("/go")
    public List<Access> index() {

        List<Room> rooms = accessSync.fetchRoom();

            List<Key> keys = accessSync.fetchKey();

        List<Access> accesses = new ArrayList<>();
        for (Booking b : bookingService.allBookings()) {
            for (Key key : keys) {
                for (Room room : rooms) {
                    if (key.owner().equals(b.getUsername()) &&
                            room.raum().equals(workspaceService.findSpacesById((long)b.getWorkspaceID()).getRoomname())) {
                        accesses.add(new Access(key.id(), room.id()));
                    }
                }
            }
        }


        return accesses;
    }
    }