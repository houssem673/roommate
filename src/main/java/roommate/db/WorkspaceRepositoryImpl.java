package roommate.db;

import org.springframework.stereotype.Repository;
import roommate.application.ports.WorkspaceRepository;
import roommate.db.DTO.EquipmentDTO;
import roommate.db.DTO.WorkspaceDTO;
import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class WorkspaceRepositoryImpl implements WorkspaceRepository {
    private final WorkspaceDAO workspaceRepo;

    public WorkspaceRepositoryImpl(WorkspaceDAO workspaceRepo) {
        this.workspaceRepo = workspaceRepo;
    }

    @Override
    public List<Workspace> findAllWorkspaces() {
        Collection<WorkspaceDTO> workspaceDTO = workspaceRepo.findAll();
        return workspaceDTO.stream().map(Adapter::workspaceDTOToWorkspaceDomain).toList();
    }

    @Override
    public Optional<Workspace> findById(Long id) {
        return workspaceRepo.findById(id).map(Adapter::workspaceDTOToWorkspaceDomain);
    }

    @Override
    public Workspace save(Workspace workspace) {
            Set<EquipmentDTO> equipmentsDTO = workspace.getEquipments().stream().map(Adapter::equipmentDomainToEquipmentDTO).collect(Collectors.toSet());
            WorkspaceDTO workspaceDTO = new WorkspaceDTO(workspace.getId(), workspace.getName(), equipmentsDTO, workspace.getRoomname());
            WorkspaceDTO save = workspaceRepo.save(workspaceDTO);
            return Adapter.workspaceDTOToWorkspaceDomain(save);
    }

    @Override
    public void delete(Workspace workspace) {
        WorkspaceDTO workspaceDTO = Adapter.workspaceDomainToWorkspaceDTO(workspace);
        workspaceRepo.delete(workspaceDTO);
    }

    public boolean hasCertainEquipment(Workspace workspace, Set<Equipment> equipment) {
        if (equipment == null) return false;
        Set<EquipmentDTO> actualEquipment = workspaceRepo.findAllEquipmentOfAWorkspace(workspace.getId());
        return actualEquipment.containsAll(equipment.stream().map(Adapter::equipmentDomainToEquipmentDTO).collect(Collectors.toSet()));
    }

    @Override
    public Workspace findWorkspaceByName(String name) {
        WorkspaceDTO workspaceByName = workspaceRepo.findWorkspaceByName(name);
        return Adapter.workspaceDTOToWorkspaceDomain(workspaceByName);
    }

    @Override
    public Set<Equipment> findAllEquipments() {
        Set<EquipmentDTO> allEquipmentDTOs = workspaceRepo.findAllEquipments();
        return allEquipmentDTOs.stream().map(Adapter::equipmentDTOToEquipmentDomain).collect(Collectors.toSet());
    }


}
