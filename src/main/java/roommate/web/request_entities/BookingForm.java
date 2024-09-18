package roommate.web.request_entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingForm {

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private int workspaceId;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }
}
