package roommate.web.request_entities;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import roommate.domain.model.Equipment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
public class FilterForm {
    @FutureOrPresent(message = "Date must be present or in the future")

    @NotNull(message = "please enter a valid date")
    private LocalDate date;

    @NotNull(message = "please enter a start time")
    private LocalTime start;

    @NotNull(message = "please enter a end time")
    private LocalTime end;
    private Set<Equipment> equipments;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }
}