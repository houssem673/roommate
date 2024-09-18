package roommate.db;

import org.springframework.stereotype.Repository;
import roommate.application.ports.BookingRepository;
import roommate.db.DTO.BookingDTO;
import roommate.domain.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class BookingRepositoryImpl implements BookingRepository {


    private final BookingDAO repo;


    public BookingRepositoryImpl(BookingDAO repo) {
        this.repo = repo;
    }


    @Override
    public List<Booking> findAll() {
        List<BookingDTO> bookingDTOS = repo.findAll();
        return bookingDTOS.stream().map(Adapter::bookingDTOToBookingDomain).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAvailableWorkspaceids(LocalDate filterDay, LocalTime startTime, LocalTime endTime) {
        return repo.findAvailableWorkspaceids(filterDay,startTime,endTime);
    }
    @Override
    public void saveBooking(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO(booking.getId(), booking.getBookingDay(),booking.getStartTime(), booking.getEndTime(),booking.getWorkspaceID(), booking.getUsername());
        repo.save(bookingDTO);

    }

    @Override
    public Optional<Booking> findBookingById(Long id) {
        Optional<BookingDTO> bookingDTOById = repo.findBookingById(id);
        return bookingDTOById.map(Adapter::bookingDTOToBookingDomain);
    }

    @Override
    public List<Booking> findBookingByUsername(String username) {
        List<BookingDTO> bookingDTOs = repo.findBookingByUsername(username);
        return bookingDTOs.stream().map(Adapter::bookingDTOToBookingDomain).toList();
    }


    @Override
    public int updateBooking(Long id, LocalDate day, LocalTime startTime, LocalTime endTime, Long workspaceId) {
        if(!repo.checkAvailabilty(day,startTime,endTime, workspaceId)){
            return 0;
        }
        return repo.updateBooking(id,day,startTime,endTime, workspaceId);

    }

    @Override
    public void deleteByWorkspaceID(int id) {
        repo.deleteByWorkspaceID(id);
    }

    @Override
    public void delete(Booking booking) {
        BookingDTO bookingDTO = Adapter.bookingDomainToBookingDTO(booking);
        repo.delete(bookingDTO);
    }

    @Override
    public void deleteBookingForBlocking(Long workspaceId, LocalDate day, LocalTime startTime, LocalTime endTime) {
        repo.deleteBookingForBlocking(workspaceId,day,startTime,endTime);
    }


}
