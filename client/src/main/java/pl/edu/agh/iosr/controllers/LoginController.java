package pl.edu.agh.iosr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.services.RoleService;
import pl.edu.agh.iosr.services.TenantService;
import pl.edu.agh.iosr.services.UserService;


@Controller
public class LoginController{
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private TenantService tenantService;
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(ModelMap model) {
    	
    	List<Tenant> tenantList = tenantService.getAllTenants();
    	Map<String,String> tenantMap = new HashMap<String,String>();
    	for(Tenant tenant : tenantList){
    		tenantMap.put(tenant.getName(), tenant.getDescription());
    	}
    	
    	List<Role> roleList = roleService.getAllRoles();
    	Map<String,String> roleMap = new HashMap<String,String>();
    	for(Role role : roleList){
    		roleMap.put(role.getName(), role.getName());
    	}
    	
    	model.put("tenantMap", tenantMap);
    	model.put("roleMap", roleMap);
        return "signup";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(ModelMap model, 
    		@RequestParam("login")String userName, 
    		@RequestParam("password")String password,
    		@RequestParam("email")String mailId,
    		@RequestParam("name")String name,
    		@RequestParam("surname")String surname,
    		@RequestParam("tenant")String tenantName,
    		@RequestParam("role")String roleName) {
    	//TODO: use command object and @ModelAttribute instead of @RequestParam
    	
        UserEntity user = userService.getUserByLogin(userName);
        if (user == null) {
            Role role = roleService.getRoleByName(roleName);
            Tenant tenant = tenantService.getTenantByName(tenantName);
            
            user = new UserEntity(userName, password, mailId);
            user.setRole(role);
            user.setEnabled(true);
            user.setName(name);
            user.setSurname(surname);
            user.setTenant(tenant);
            
            user = userService.createUser(user);
            String succMsg = "User registration succeeds ";
            return "redirect:/login?succMsg=" + succMsg;
        } else {
            String errorMsg = "User registration fails already same user name exists please try "
                 + "with different user name";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/signup?errorMsg=" + errorMsg;
        }
    }

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