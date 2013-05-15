package pl.edu.agh.iosr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.services.RoleService;
import pl.edu.agh.iosr.services.TenantService;
import pl.edu.agh.iosr.services.UserService;


@Controller
public class LoginController{
	//private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/sessiontimeout", method = RequestMethod.GET)
    public String sessionTimeout(ModelMap model) {
        String errorMsg = "Session has expired. Please login";
        model.addAttribute("errorMsg", errorMsg);
        return "login";
    }
}