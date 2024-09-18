package roommate.application.ports;


import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WorkspaceRepository {
    List<Workspace> findAllWorkspaces();
    Optional<Workspace> findById(Long id);
    Workspace save(Workspace workspace);
    void delete(Workspace workspace);
    //void updateWorkspaceById(@Param("id") Long id);
    boolean hasCertainEquipment(Workspace workspace, Set<Equipment> equipment);
    Workspace findWorkspaceByName(String name);
    Set<Equipment> findAllEquipments();
}
