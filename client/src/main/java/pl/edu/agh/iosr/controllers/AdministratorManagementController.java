package pl.edu.agh.iosr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import pl.edu.agh.iosr.model.command.UserCommand;
import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.services.RoleService;
import pl.edu.agh.iosr.services.TenantService;
import pl.edu.agh.iosr.services.UserService;

@Controller
public class AdministratorManagementController extends AbstractUserController{
	
	private final Logger log = LoggerFactory.getLogger(AdministratorManagementController.class);

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<UserEntity> administratorList = userService.getAllUsersOfRole("ROLE_ADMIN");
		
		model.put("administratorList", administratorList);
		return "administrator/list";
	}

    @RequestMapping(value = "/administrator/add", method = RequestMethod.GET)
    public String signUp(ModelMap model) {
        model.put("userCommand", prepareUserCommand(null,null,Role.ROLE_ADMIN,prepareTenantMap(false), null) );
        return "administrator/add";
    }

    @RequestMapping(value = "/administrator/add", method = RequestMethod.POST)
    public String signUp(ModelMap model,
        @ModelAttribute("userCommand") UserCommand userCommand) {

        UserEntity user = userService.getUserByLogin(userCommand.getLogin());
        if (user == null) {
            Role role = roleService.getRoleByName(Role.ROLE_ADMIN);
            Tenant tenant = tenantService.getTenantByName(userCommand.getTenantName());

            user = userCommand.getUser();
            user.setRole(role);
            user.setEnabled(true);
            user.setTenant(tenant);

            user = userService.createUser(user);
            String succMsg = "Administrator creation succeeds ";
            return "redirect:/administrator/list?succMsg=" + succMsg;
        } else {
            String errorMsg = "Administrator creation fails already same user name exists please try "
                    + "with different user name";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/administrator/add?errorMsg=" + errorMsg;
        }
    }

    @RequestMapping(value = "/administrator/remove", method = RequestMethod.POST)
    public String remove(ModelMap model,
        @RequestParam(value = "itemIds", required = false) long[] itemIds ) {
        if(itemIds == null){
            itemIds = new long[]{};
        }
        String errorMsg = "";
        for(Long id : itemIds){
            log.debug("item to be removed: "+id);
            try{
                userService.removeUserById(id);
            }catch(Exception e){
                log.error("error while removing tenent",e);
                errorMsg = "There were some errors when removing tenents";
            }
        }
        if(errorMsg.equals("")){
            return "redirect:/tenant/list";
        }
        return "redirect:/tenant/list?errorMsg="+errorMsg;
    }

    @RequestMapping(value = "/administrator/edit/{id}", method = RequestMethod.GET)
    public String editForm(ModelMap model,
       @PathVariable Long id){

        UserEntity administrator = userService.getUserById(id);

        if(administrator == null){
            String errorMsg = "No such user";
            return "redirect:/administrator/list?succMsg=" + errorMsg;
        }

        model.put("userCommand",
                prepareUserCommand(
                        administrator,
                        administrator.getTenant().getName(),
                        administrator.getRole().getName(),
                        prepareTenantMap(false),
                        null));
        return "administrator/edit";
    }

    @RequestMapping(value = "/administrator/edit/{id}", method = RequestMethod.POST)
    public String edit(ModelMap model,
        @PathVariable Long id,
        @ModelAttribute("userCommand") UserCommand userCommand){

        log.debug("User command = "+userCommand);

        UserEntity administrator = userService.getUserById(id);

        if (administrator == null) {
            String errorMsg = "Cannot modify administrator!";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/administrator/edit/"+id+"?errorMsg=" + errorMsg;
        }
        try{
            administrator.setLogin(userCommand.getLogin());
            administrator.setPassword(userCommand.getPassword());
            administrator.setEmail(userCommand.getEmail());
            administrator.setName(userCommand.getName());
            administrator.setSurname(userCommand.getSurname());
            administrator.setEnabled(userCommand.getEnabled());
            administrator.setTenant(tenantService.getTenantByName(userCommand.getTenantName()));

            administrator = userService.merge(administrator);
        }catch (Exception e){
            log.error("Exception while updating administrator",e);
            String errorMsg = "Cannot modify administrator!";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/administrator/edit/"+id+"?errorMsg=" + errorMsg;
        }
        String succMsg = "Tenant successfully edited ";
        return "redirect:/administrator/list?succMsg=" + succMsg;
    }
}
