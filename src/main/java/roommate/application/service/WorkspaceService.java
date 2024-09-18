package roommate.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roommate.application.ports.WorkspaceRepository;
import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;
import roommate.application.custom_exception.WorkspaceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public Long addWorkspace(Workspace workspace) {
        Workspace newWorkspace = new Workspace(null, workspace.getName(),new HashSet<Equipment>(), workspace.getName());
        return workspaceRepository.save(newWorkspace).getId();


    }
    @Transactional
    public void addEquipments(Long id, String description) {
        Workspace workspace = findSpacesById(id);
        Set<Equipment> equipments = workspace.getEquipments().stream().map(equipment -> new Equipment(equipment.description())).collect(Collectors.toSet());
        equipments.add(new Equipment(description));
        Workspace workspace1 = workspace.addEquipment(equipments);
        workspaceRepository.save(workspace1);
    }

    public List<Workspace> findAllWorkspaces() {
        return workspaceRepository.findAllWorkspaces().stream().sorted(Comparator.comparing(Workspace::getId)).toList();
    }
    @Transactional
    public Workspace findSpacesById(Long id) {
        return workspaceRepository.findById(id).orElseThrow(WorkspaceNotFoundException::new);
    }
    public Workspace findWorkspaceByName(String name){
        return workspaceRepository.findWorkspaceByName(name);
    }
    public void deleteWorkspace(Long id){
        Optional<Workspace> w = workspaceRepository.findById(id);
        w.ifPresent(workspaceRepository::delete);
    }
    @Transactional
    public void editWorkspace(Long id, String name, String roomname){
        Workspace workspace = workspaceRepository.findById(id).get();
        workspace.setName(name);
        workspace.setRoomname(roomname);
        workspaceRepository.save(workspace);
    }
    public Set<Equipment> getAllEquipments(){
        return workspaceRepository.findAllEquipments();
    }
}
