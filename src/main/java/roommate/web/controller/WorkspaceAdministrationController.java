package roommate.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import roommate.application.service.BookingService;
import roommate.application.service.WorkspaceService;
import roommate.domain.model.Workspace;

import java.sql.SQLException;
import java.util.List;

@Controller
public class WorkspaceAdministrationController {
    private final WorkspaceService workspaceService;
    private final BookingService bookingService;

    public WorkspaceAdministrationController(WorkspaceService service, BookingService bookingService) {
        this.workspaceService = service;
        this.bookingService = bookingService;
    }


    // adminpage um entweder zu Buchungen oder workspaces gelangen
    @GetMapping("/admin")
    public String viewAdminPage() {
        return "/Admin/AdminPage";
    }

    @GetMapping("/admin/workspaces")
    public String allWorkspaces(Model model) {
        List<Workspace> workspaces = workspaceService.findAllWorkspaces();
        model.addAttribute("workspace", workspaces);
        return "/Admin/WorkspaceAdministration";
    }
    @GetMapping("/admin/workspaces/add-new-workspace")
    public String addNewWorkspacePage(Model model) {
        List<Workspace> workspaces = workspaceService.findAllWorkspaces();
        model.addAttribute("workspace", workspaces);
        return "/Admin/AddNewWorkspace";
    } // neue Arbeitsplätze
    @GetMapping("/admin/workspaceDetails")
    public String details(Model model, long nr) {
        Workspace workspace = workspaceService.findSpacesById(nr);
        model.addAttribute("workspace",workspace);
        return "/Admin/workspaceDetails";
    }


    @PostMapping("/admin/add-new-workspace") // admin only
    public String addNewWorkspace(String workspace,String roomname) throws SQLException {
        Workspace saveWorkspace = new Workspace(workspace, roomname);
        Long id = workspaceService.addWorkspace(saveWorkspace);

        return "redirect:/admin/workspaceDetails?nr="+id;
    }

    //pessimistisch
    @PostMapping("/admin/addEquipment") // admin only
    public String addNewEquipment(Long id, String equipment) {
        workspaceService.addEquipments(id,equipment);
        return "redirect:/admin/workspaceDetails?nr="+id;
    }


    @GetMapping("/view-bookings") // admin only
    public String viewBookingsPage() {
        return "Admin/BookingAdministration";
    } // Buchungen einsehen


    //WorkspaceNotFoundException werfen, wenn id schon gelöscht, oder nicht existent
    @PostMapping("/admin/workspaces/delete/{id}") // admin only
    public String deleteWorkspace(@PathVariable("id") Long id) {
        long id2 = id;
        int workspaceID = (int) id2;
        bookingService.deleteByWorkspaceID(workspaceID);
        workspaceService.deleteWorkspace(id);
        return "redirect:/admin/workspaces";
    }


    @GetMapping("/admin/workspaces/edit/{id}")
    public String editWorkspace(@PathVariable("id") Long id, Model model) {
        Workspace workspace = workspaceService.findSpacesById(id);
        model.addAttribute("workspace", workspace);
        return "/Admin/EditWorkspace";
    }


    @PostMapping("/admin/workspaces/edit/{id}") // admin only
    public String updateWorkspace(@PathVariable("id") Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String roomname) {
        workspaceService.editWorkspace(id, name, roomname);
        return "redirect:/admin/workspaces";
    }


}
