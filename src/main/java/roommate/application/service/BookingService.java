package roommate.application.service;

import org.springframework.stereotype.Service;
import roommate.application.ports.BookingRepository;
import roommate.domain.model.Booking;
import roommate.application.custom_exception.BookingNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    // Administration Stuff

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking getBookingById(Long id){
        return repository.findBookingById(id).orElseThrow(BookingNotFoundException::new);
    }

    public List<Booking> allBookings() {
        return (List<Booking>) repository.findAll();
    }

    public void saveBooking(Booking booking) {
        repository.saveBooking(booking);
    }
    public List<Booking> findAllBookingsByUser(String username){
        return repository.findBookingByUsername(username);
    }

    public int updateBooking(Long id, LocalDate day, LocalTime startTime, LocalTime endTime, Long workspaceId){
        int i = repository.updateBooking(id, day, startTime, endTime, workspaceId);
        return i;
    }

    public void deleteByWorkspaceID(int id){
        repository.deleteByWorkspaceID(id);
    }

    public void deleteBooking(Booking booking){
        repository.delete(booking);
    }
    public void deleteBookingForBlocking(Long workspaceId,
                                         LocalDate day,
                                         LocalTime startTime,
                                         LocalTime endTime){
        repository.deleteBookingForBlocking(workspaceId, day, startTime, endTime);
    }
}
