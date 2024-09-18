package roommate.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

// dummy
public class Booking{
private  final Long id;
private final LocalDate bookingDay;
private final LocalTime startTime;
private final LocalTime endTime;
private final String username;
private final int workspaceID;
//List<Workspace> workspaces;
public Booking(Long id, LocalDate bookingDay, LocalTime startTime, LocalTime endTime, int workspaceID, String username) {
    this.id = id;
    this.workspaceID=workspaceID;
    this.bookingDay = bookingDay;
    this.startTime = startTime;
    this.endTime = endTime;
    this.username = username;
}
    public Booking(LocalDate bookingDay, LocalTime startTime, LocalTime endTime, int workspaceID, String username) {
        this(null, bookingDay,startTime,endTime,workspaceID,username);
    }


    public Long getId() {
        return id;
    }

    public LocalDate getBookingDay() {
        return bookingDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getUsername() {
        return username;
    }

    public int getWorkspaceID() {
        return workspaceID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
