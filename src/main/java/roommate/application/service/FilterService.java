package roommate.application.service;


import org.springframework.stereotype.Service;
import roommate.application.ports.BookingRepository;
import roommate.application.ports.WorkspaceRepository;
import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FilterService {
    WorkspaceRepository workspaceRepository;
    private final BookingRepository bookingRepository;

    public FilterService(WorkspaceRepository repository, BookingRepository bookingRepository) {
        this.workspaceRepository = repository;
        this.bookingRepository = bookingRepository;
    }


    public List<Workspace> filterSpaceAndEquipment(LocalDate day, LocalTime start, LocalTime end, Set<Equipment> equipment) {
        List<Long> availableWorkspaceIds = bookingRepository.findAvailableWorkspaceids(day, start, end);

        List<Workspace> filteredWorkspaces = new ArrayList<>();
        for (Long id : availableWorkspaceIds) {
            Workspace workspace = workspaceRepository.findById(id).orElseThrow();
            if (equipment ==null || workspaceRepository.hasCertainEquipment(workspace, equipment)) {
                filteredWorkspaces.add(workspace);
            }

        }
        return filteredWorkspaces;
    }


}
