package roommate.db.DTO;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.Set;
@Table("workspace")
public record WorkspaceDTO(@Id Long id, String name, Set<EquipmentDTO> equipments, String roomname) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceDTO that = (WorkspaceDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(equipments, that.equipments) && Objects.equals(roomname, that.roomname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, equipments, roomname);
    }
}
