package roommate.db;

import roommate.db.DTO.BookingDTO;
import roommate.db.DTO.EquipmentDTO;
import roommate.db.DTO.WorkspaceDTO;
import roommate.domain.model.Booking;
import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;

import java.util.Set;
import java.util.stream.Collectors;


public class Adapter {

    static Booking bookingDTOToBookingDomain(BookingDTO bookingDTO){
        return new Booking(bookingDTO.id(),bookingDTO.bookingDay(),bookingDTO.startTime(),bookingDTO.endTime(),bookingDTO.workspaceID(),bookingDTO.username());
    }

    static Workspace workspaceDTOToWorkspaceDomain(WorkspaceDTO workspaceDTO){
        Set<Equipment> equipment = workspaceDTO.equipments().stream().map(Adapter::equipmentDTOToEquipmentDomain).collect(Collectors.toSet());
        Workspace workspace = new Workspace(workspaceDTO.id(), workspaceDTO.name(),equipment, workspaceDTO.roomname());
        return workspace;
    }

    static BookingDTO bookingDomainToBookingDTO(Booking booking) {
        return new BookingDTO(booking.getId(),booking.getBookingDay(),booking.getStartTime(),booking.getEndTime(),booking.getWorkspaceID(),booking.getUsername());
    }

    static WorkspaceDTO workspaceDomainToWorkspaceDTO(Workspace workspace) {
        Set<EquipmentDTO> equipmentDTOSet = workspace.getEquipments().stream().map(Adapter::equipmentDomainToEquipmentDTO).collect(Collectors.toSet());
        return new WorkspaceDTO(workspace.getId(), workspace.getName(), equipmentDTOSet, workspace.getRoomname());
    }
    static EquipmentDTO equipmentDomainToEquipmentDTO(Equipment equipment) {
        return new EquipmentDTO(equipment.description());
    }
    static Equipment equipmentDTOToEquipmentDomain(EquipmentDTO equipment) {
        return new Equipment(equipment.description());
    }
}
