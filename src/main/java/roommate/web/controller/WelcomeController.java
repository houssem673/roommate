package roommate.web.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome(Model model, OAuth2AuthenticationToken token){
        boolean isAdmin = token.getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (isAdmin){
            model.addAttribute("admin",token.getPrincipal().getAttribute("login"));
        }
        return "WelcomePage";
    }

}
