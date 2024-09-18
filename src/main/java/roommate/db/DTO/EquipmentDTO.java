package roommate.db.DTO;

import org.springframework.data.relational.core.mapping.Table;

@Table("equipment")
public record EquipmentDTO(String description) {
}
