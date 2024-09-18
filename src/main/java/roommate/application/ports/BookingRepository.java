package roommate.application.ports;

import roommate.domain.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    List<Booking> findAll();
    List<Long> findAvailableWorkspaceids(LocalDate filterDay,
                                           LocalTime startTime,
                                           LocalTime endTime);
    void saveBooking(Booking booking);
    Optional<Booking> findBookingById(Long id);
    List<Booking> findBookingByUsername(String username);
    int updateBooking(Long id, LocalDate day, LocalTime startTime, LocalTime endTime, Long workspaceId);
    void deleteByWorkspaceID(int id);
    void delete(Booking booking);

    void deleteBookingForBlocking(Long workspaceId,
                                  LocalDate day,
                                  LocalTime startTime,
                                  LocalTime endTime
    );
}
