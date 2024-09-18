package roommate.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roommate.application.service.BookingService;
import roommate.application.service.WorkspaceService;
import roommate.domain.model.Workspace;
import roommate.security.helper.WithMockOAuth2User;
import roommate.web.controller.WorkspaceAdministrationController;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceAdministrationController.class)
public class ControllerAdminTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    WorkspaceService workspaceService;
    @MockBean
    BookingService bookingService;

    @Test
    @WithMockOAuth2User
    @DisplayName("Adminpage is available and routes to the correct html ")
    void test_1() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Admin/AdminPage"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("WorkspaceAdminPage is available and routes to the correct html and has the correct Model")
    void test_2() throws Exception {
        Workspace workspace = new Workspace(1L, "test", new HashSet<>(), "hallo");
        when(workspaceService.findAllWorkspaces()).thenReturn(List.of(workspace));
        mockMvc.perform(get("/admin/workspaces"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Admin/WorkspaceAdministration"))
                .andExpect(model().attribute("workspace", List.of(workspace)));
    }


    @Test
    @WithMockOAuth2User
    @DisplayName("addWorkspacePage is available and routes to the correct html")
    void test_3() throws Exception {
        mockMvc.perform(get("/admin/workspaces/add-new-workspace"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Admin/AddNewWorkspace"));
    }


    @Test
    @WithMockOAuth2User
    @DisplayName("bookingPage is available and routes to the correct html")
    void test_4() throws Exception {
        mockMvc.perform(get("/view-bookings"))
                .andExpect(status().isOk())
                .andExpect(view().name("Admin/BookingAdministration"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("post request for addWorkspace and redirect, valid if redirected")
    void test_5() throws Exception {
        mockMvc.perform(post("/admin/add-new-workspace").with(csrf())
                .param("workspace", "WorkspaceTest")
                .param("roomname", "Business")).andExpect(status().is3xxRedirection());
        verify(workspaceService, times(1)).addWorkspace(new Workspace("WorkspaceTest", "Business"));
    }


    @Test
    @WithMockOAuth2User
    @DisplayName("correct route when a workspace is going to be removed")
    void test_7() throws Exception {
        mockMvc.perform(post("/admin/workspaces/delete/{id}", 0)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(workspaceService, times(1)).deleteWorkspace(0L);
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("correct route for delete Workspace with different id")
    void test_8() throws Exception {
        mockMvc.perform(post("/admin/workspaces/delete/{id}", 1).with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(workspaceService, times(1))
                .deleteWorkspace(1L);

    }

    @Test
    @WithMockOAuth2User
    @DisplayName("correct route for delete Workspace with different id and guarantee that wrong id doesn't pass.")
    void test_9() throws Exception {
        when(workspaceService.findSpacesById(3L)).thenReturn(new Workspace(3L, "Test", "Test1"));
        mockMvc.perform(post("/admin/workspaces/delete/3").with(csrf()));
        verify(workspaceService, only()).deleteWorkspace(3L);
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("test correct renaming of workspace")
    void test_10() throws Exception{
        when(workspaceService.findSpacesById(2L)).thenReturn(new Workspace(2L, "Test", "Test1"));
        mockMvc.perform(post("/admin/workspaces/edit/{id}",2)
                .param("name","Stelio")
                .param("roomname","stinkt")
                .with(csrf())).andExpect(status().is3xxRedirection());
        verify(workspaceService, times(1)).editWorkspace(2L,"Stelio", "stinkt");
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("correct route, ressources and html name for certain workspace")
    void test_11() throws Exception {
        Workspace workspace = new Workspace(2L, "Test", "Test1");
        when(workspaceService.findSpacesById(2L)).thenReturn(workspace);
        mockMvc.perform(get("/admin/workspaces/edit/{id}",2))
                .andExpect(view().name("/Admin/EditWorkspace"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("workspace",workspace))
                .andExpect(model().attributeExists("workspace"));
    }

//    @Test
//    @WithMockOAuth2User
//    @DisplayName("details are reachable, workspaces are available")
//    void test_12() throws Exception {
//        Workspace workspace = new Workspace(5L, "Propra", "Klausur");
//        workspace.addEquipment(Set.of(new Equipment("test")));
//        when(workspaceService.findSpacesById(5L)).thenReturn(workspace);
//        mockMvc.perform(get("/admin/workspaceDetails").param("nr","5"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/Admin/workspaceDetails"))
//                .andExpect(model().attributeExists("workspace"))
//                .andExpect(model().attribute("workspace",workspace));
//    }

    @Test
    @WithMockOAuth2User
    @DisplayName("equipment can get added")
    void test_13() throws Exception{
        mockMvc.perform(post("/admin/addEquipment")
                .param("id", String.valueOf(1L))
                .param("equipment","Tisch").with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(workspaceService,times(1)).addEquipments(1L,"Tisch");
    }
}
