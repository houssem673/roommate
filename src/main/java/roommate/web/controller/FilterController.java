package roommate.web.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import roommate.application.service.FilterService;
import roommate.application.service.WorkspaceService;
import roommate.domain.model.Equipment;
import roommate.domain.model.Workspace;
import roommate.web.request_entities.FilterForm;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class FilterController {

    private final WorkspaceService workspaceService;
    private final FilterService filterService;

    public FilterController(WorkspaceService workspaceService, FilterService filterService) {
        this.workspaceService = workspaceService;
        this.filterService = filterService;
    }

    @GetMapping("/booking")
    public String menu(Model model, @ModelAttribute("filterForm") FilterForm filterForm) {
        Set<Equipment> equipments = workspaceService.getAllEquipments();
        model.addAttribute("equipments", equipments);
        return "/Menu/FilterMenu";
    }

    @Transactional
    @GetMapping("/filter-booking")
    public String filter( @Valid FilterForm filterForm, BindingResult result, Model model) {
        if (result.hasErrors()){
            Set<Equipment> equipments = workspaceService.getAllEquipments();
            model.addAttribute("equipments", equipments);
            return "/Menu/FilterMenu";
        }
        List<Workspace> filteredWorkspaces = filterService.filterSpaceAndEquipment(filterForm.getDate(),
                filterForm.getStart(), filterForm.getEnd(), filterForm.getEquipments());
        filteredWorkspaces.sort(Comparator.comparing(Workspace::getName));
        model.addAttribute("workspaces", filteredWorkspaces);
        return "/Menu/AvailableWorkspaces";
    }



}


