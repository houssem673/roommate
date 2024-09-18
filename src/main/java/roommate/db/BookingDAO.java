package roommate.db;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import roommate.db.DTO.BookingDTO;
import roommate.domain.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingDAO extends CrudRepository<BookingDTO,Long> {
    List<BookingDTO> findAll();
    Optional<BookingDTO> findBookingById(Long id);

    @Query("SELECT DISTINCT w.id " +
            "FROM workspace w " +
            "FULL OUTER JOIN booking b On w.id = b.workspace_id " +
            "WHERE w.id " +
            "NOT IN(SELECT workspace_id " +
            "FROM booking " +
            "WHERE booking_day = :booking_day AND (end_time > :start_time AND start_time < :end_time))")
    List<Long> findAvailableWorkspaceids(@Param("booking_day") LocalDate booking_day,
                                         @Param("start_time") LocalTime startTime,
                                         @Param("end_time") LocalTime endTime);


    @Query("SELECT case when count(*) =0 then true else false END as isAvailable FROM booking WHERE booking_day = :booking_day  AND (end_time > :start_time AND start_time < :end_time) and workspace_id=:workspace_id")
    boolean checkAvailabilty(@Param("booking_day") LocalDate booking_day, @Param("start_time") LocalTime startTime,  @Param("end_time") LocalTime endTime, @Param("workspace_id") Long workspaceId);

   List<BookingDTO> findBookingByUsername(String username);
    @Modifying
    @Query("UPDATE booking SET booking_day = :booking_day, start_time = :start_time, end_time = :end_time, workspace_id = :workspace_id WHERE id = :id")
    int updateBooking(@Param("id") Long id,
                      @Param("booking_day") LocalDate booking_day,
                      @Param("start_time") LocalTime startTime,
                      @Param("end_time") LocalTime endTime,
                      @Param("workspace_id") Long workspaceId);
    @Modifying
    @Query("DELETE FROM booking where workspace_id = :workspace_id")
    void deleteByWorkspaceID(@Param("workspace_id") int id);
    @Modifying
    @Query("DELETE FROM booking WHERE workspace_id = :workspace_id AND booking_day = :booking_day AND end_time > :start_time AND start_time < :end_time")
    void deleteBookingForBlocking(@Param("workspace_id") Long workspaceId,
                                  @Param("booking_day") LocalDate booking_day,
                                  @Param("start_time") LocalTime startTime,
                                  @Param("end_time") LocalTime endTime
                                   );

}

