package roommate.db;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import roommate.db.DTO.EquipmentDTO;
import roommate.db.DTO.WorkspaceDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WorkspaceDAO extends CrudRepository<WorkspaceDTO, Long> {
    List<WorkspaceDTO> findAll();
    @Lock(LockMode.PESSIMISTIC_WRITE)
    Optional<WorkspaceDTO> findById(Long id);
    @Query("SELECT description FROM equipment WHERE workspace= :workspace")
    Set<EquipmentDTO> findAllEquipmentOfAWorkspace(@Param("workspace") Long workspace);

    WorkspaceDTO findWorkspaceByName(String name);

    @Modifying
    @Query("UPDATE workspace SET name = COALESCE(:name, name), roomname = COALESCE(:roomname, roomname) WHERE id = :id")
    void updateById(@Param("id") Long id, @Param("name") String name, @Param("roomname") String roomname);

    @Query("SELECT description FROM equipment")
    Set<EquipmentDTO> findAllEquipments();

}
