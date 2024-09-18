package roommate.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roommate.application.service.FilterService;
import roommate.application.service.WorkspaceService;
import roommate.domain.model.Equipment;
import roommate.security.helper.WithMockOAuth2User;
import roommate.web.controller.FilterController;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilterController.class)
public class FilterMvcTest {

    @MockBean
    private FilterService filterService;
    @MockBean
    private WorkspaceService workspaceService;
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Filterform has all the equipments")
    @WithMockOAuth2User(login = "ozan")
    void test_1() throws Exception {
        Set<Equipment> equipment = Set.of(new Equipment("a"));
        when(workspaceService.getAllEquipments()).thenReturn(equipment);
        mvc.perform(get("/booking"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Menu/FilterMenu"))
                .andExpect(model().attribute("equipments", equipment));
    }
    @Test
    @DisplayName("Filter is working if inputs are valid")
    @WithMockOAuth2User(login = "ozan")
    void test_2() throws Exception {
        mvc.perform(get("/filter-booking")
                        .param("date", "2024-03-01")
                        .param("start", "10:00")
                        .param("end", "12:00")
                        .param("equipments", "monitor"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Menu/AvailableWorkspaces"));

        verify(filterService).filterSpaceAndEquipment(any(),any(),any(),any());
    }
    @Test
    @DisplayName("If a requested date is in past, we will view filter form again")
    @WithMockOAuth2User(login = "ozan")
    void test_3() throws Exception {
        mvc.perform(get("/filter-booking")
                        .param("date", "1900-10-02")
                        .param("start", "10:00")
                        .param("end", "12:00")
                        .param("equipments", "monitor"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Menu/FilterMenu"));
    }
}
