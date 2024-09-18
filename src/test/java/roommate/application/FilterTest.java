package roommate.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roommate.application.ports.BookingRepository;
import roommate.application.ports.WorkspaceRepository;
import roommate.application.helper.FilterBuilder;
import roommate.application.service.FilterService;
import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;
import roommate.web.request_entities.FilterForm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilterTest {

    BookingRepository bookingRepository = mock(BookingRepository.class);
    WorkspaceRepository workspaceRepository = mock(WorkspaceRepository.class);



    @Test
    @DisplayName("not booked Workspace shows up in filtering ")
    void test1() {
        List<Long> availiable = new ArrayList<>();
        availiable.add(1L);
        Workspace workspace = new Workspace("test");
        FilterForm form = new FilterBuilder()
                .withDate(LocalDate.of(2024, 2, 10))
                .withStartTime(LocalTime.of(9, 0, 0))
                .withEndTime(LocalTime.of(12, 0, 0))
                .withEquipment(Set.of(new Equipment("Hardware")))
                .build();
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(bookingRepository.findAvailableWorkspaceids(form.getDate(), form.getStart(), form.getEnd())).thenReturn(availiable);



        FilterService service = new FilterService(workspaceRepository, bookingRepository);
        List<Workspace> filtered = service.filterSpaceAndEquipment(form.getDate(), form.getStart(), form.getEnd(),form.getEquipments());
        assertEquals(1, filtered.size());


    }
}
