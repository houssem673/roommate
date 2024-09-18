package roommate.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import roommate.domain.model.Workspace;
import roommate.application.service.WorkspaceService;

import java.util.List;

@Controller
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping("/workspaces")
    public String showAllWorkspaces(Model model){
        List<Workspace> workspaces = workspaceService.findAllWorkspaces();
        model.addAttribute("workspace", workspaces);
        return "/Menu/ListAllWorkspaces";
    }
}
