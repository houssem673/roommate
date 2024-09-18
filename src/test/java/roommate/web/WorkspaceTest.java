package roommate.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roommate.application.service.WorkspaceService;
import roommate.security.helper.WithMockOAuth2User;
import roommate.web.controller.WorkspaceController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceController.class)

public class WorkspaceTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    WorkspaceService workspaceService;

    @Test
    @DisplayName("Get request Workspaces successful and view ListAllWorkspaces if authorized")
    @WithMockOAuth2User(login = "ozanozden")
    void test_01() throws Exception {
        mockMvc.perform(get("/workspaces"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Menu/ListAllWorkspaces"));
    }

    @Test
    @DisplayName("User will be redirected to GitHub login auth page")
    void test_02() throws Exception {
        mockMvc.perform(get("/workspaces"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Get Request Workspaces has Attribute workspaces")
    @WithMockOAuth2User(login = "ozanozden")
    void test_03() throws Exception {
        mockMvc.perform(get("/workspaces"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("workspace", workspaceService.findAllWorkspaces()));
    }

}
