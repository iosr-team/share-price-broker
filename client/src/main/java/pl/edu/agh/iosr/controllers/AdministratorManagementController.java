package pl.edu.agh.iosr.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.iosr.model.command.UserCommand;
import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

@Controller
public class AdministratorManagementController extends AbstractUserController{
	
	private final Logger log = LoggerFactory.getLogger(AdministratorManagementController.class);

    private static final String LIST_ADMINISTRATOR_VIEW = "administrator/list";
    private static final String LIST_ADMINISTRATOR_REDIRECT_VIEW = "redirect:/administrator/list";
    private static final String LIST_ADMINISTRATOR_SUCCESS_REDIRECT_VIEW = "redirect:/administrator/list?succMsg=";
    private static final String LIST_ADMINISTRATOR_ERROR_REDIRECT_VIEW = "redirect:/administrator/list?errorMsg=";

    private static final String ADD_ADMINISTRATOR_ERROR_REDIRECT_VIEW = "redirect:/administrator/add?errorMsg=";

    private static final String EDIT_ADMINISTRATOR_VIEW = "administrator/edit";
    private static final String EDIT_ADMINISTRATOR_REDIRECT_VIEW = "redirect:/administrator/edit/";

    private static final String ERROR_MSG_PARAM = "?errorMsg=";

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<UserEntity> administratorList = userService.getAllUsersOfRole("ROLE_ADMIN");
		
		model.put("administratorList", administratorList);
		return LIST_ADMINISTRATOR_VIEW;
	}

    @RequestMapping(value = "/administrator/add", method = RequestMethod.GET)
    public String signUp(ModelMap model) {
        model.put("userCommand", prepareUserCommand(null,null,Role.ROLE_ADMIN,prepareTenantMap(false), null) );
        return LIST_ADMINISTRATOR_VIEW;
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
            return LIST_ADMINISTRATOR_SUCCESS_REDIRECT_VIEW + succMsg;
        } else {
            String errorMsg = "Administrator creation fails already same user name exists please try "
                    + "with different user name";
            model.addAttribute("errorMsg", errorMsg);
            return ADD_ADMINISTRATOR_ERROR_REDIRECT_VIEW + errorMsg;
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
            return LIST_ADMINISTRATOR_REDIRECT_VIEW;
        }
        return LIST_ADMINISTRATOR_ERROR_REDIRECT_VIEW + errorMsg;
    }

    @RequestMapping(value = "/administrator/edit/{id}", method = RequestMethod.GET)
    public String editForm(ModelMap model,
       @PathVariable Long id){

        UserEntity administrator = userService.getUserById(id);

        if(administrator == null){
            String errorMsg = "No such user";
            return LIST_ADMINISTRATOR_SUCCESS_REDIRECT_VIEW + errorMsg;
        }

        model.put("userCommand",
                prepareUserCommand(
                        administrator,
                        administrator.getTenant().getName(),
                        administrator.getRole().getName(),
                        prepareTenantMap(false),
                        null));
        return EDIT_ADMINISTRATOR_VIEW;
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
            return EDIT_ADMINISTRATOR_REDIRECT_VIEW + id + ERROR_MSG_PARAM + errorMsg;
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
            return EDIT_ADMINISTRATOR_REDIRECT_VIEW + id + ERROR_MSG_PARAM + errorMsg;
        }
        String succMsg = "Tenant successfully edited ";
        return LIST_ADMINISTRATOR_SUCCESS_REDIRECT_VIEW + succMsg;
    }
}
