package pl.edu.agh.iosr.controllers;

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
import pl.edu.agh.iosr.services.TenantResolverService;

import java.util.List;

@Controller
public class UserManagementController extends AbstractUserController{
	
	private final Logger log = LoggerFactory.getLogger(UserManagementController.class);

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<UserEntity> userList = userService.getAllUsers();
		
		model.put("userList", userList);
		return "user/list";
	}

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addForm(ModelMap model) {
        model.put("userCommand", prepareUserCommand(null,null,Role.ROLE_USER,null, prepareRoleMap(false,true)) );
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String add(ModelMap model,
        @ModelAttribute("userCommand") UserCommand userCommand) {

        UserEntity user = userService.getUserByLogin(userCommand.getLogin());
        if (user == null) {
            Role role = roleService.getRoleByName(userCommand.getRoleName());
            Tenant tenant = tenantResolverService.resolveTenant();

            user = userCommand.getUser();
            user.setRole(role);
            user.setEnabled(true);
            user.setTenant(tenant);

            user = userService.createUser(user);
            String succMsg = "User creation succeeds ";
            return "redirect:/user/list?succMsg=" + succMsg;
        } else {
            String errorMsg = "User creation fails already same user name exists please try "
                    + "with different user name";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/user/add?errorMsg=" + errorMsg;
        }
    }

    @RequestMapping(value = "/user/remove", method = RequestMethod.POST)
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
            return "redirect:/user/list";
        }
        return "redirect:/user/list?errorMsg="+errorMsg;
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public String editForm(ModelMap model,
       @PathVariable Long id){

        UserEntity user = userService.getUserById(id);

        if(user == null){
            String errorMsg = "No such user";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/user/list?errorMsg=" + errorMsg;
        }

        model.put("userCommand",
                prepareUserCommand(
                        user,
                        null,
                        user.getRole().getName(),
                        null,
                        prepareRoleMap(false,true)));
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String edit(ModelMap model,
        @PathVariable Long id,
        @ModelAttribute("userCommand") UserCommand userCommand){

        log.debug("User command = "+userCommand);

        UserEntity user = userService.getUserById(id);

        if (user == null) {
            String errorMsg = "Cannot modify user!";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/user/edit/"+id+"?errorMsg=" + errorMsg;
        }
        try{
            user.setLogin(userCommand.getLogin());
            user.setPassword(userCommand.getPassword());
            user.setEmail(userCommand.getEmail());
            user.setName(userCommand.getName());
            user.setSurname(userCommand.getSurname());
            user.setEnabled(userCommand.getEnabled());
            user.setTenant(tenantResolverService.resolveTenant());
            user.setRole(roleService.getRoleByName(userCommand.getRoleName()));  //TODO: validation

            user = userService.merge(user);
        }catch (Exception e){
            log.error("Exception while updating user",e);
            String errorMsg = "Cannot modify user!";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/user/edit/"+id+"?errorMsg=" + errorMsg;
        }
        String succMsg = "Tenant successfully edited ";
        return "redirect:/user/list?succMsg=" + succMsg;
    }
}
