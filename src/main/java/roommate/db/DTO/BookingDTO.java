package roommate.db.DTO;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;
@Table("booking")
public record BookingDTO(@Id Long id, LocalDate bookingDay, LocalTime startTime, LocalTime endTime, int workspaceID, String username) {
}
