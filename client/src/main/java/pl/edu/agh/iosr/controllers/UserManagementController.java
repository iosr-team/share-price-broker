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
public class UserManagementController extends AbstractUserController{
	
	private final Logger log = LoggerFactory.getLogger(UserManagementController.class);

    private static final String LIST_USER_VIEW = "user/list";
    private static final String LIST_USER_REDIRECT_VIEW = "redirect:/user/list";
    private static final String LIST_USER_SUCCESS_REDIRECT_VIEW = "redirect:/user/list?succMsg=";
    private static final String LIST_USER_ERROR_REDIRECT_VIEW = "redirect:/user/list?errorMsg=";

    private static final String ADD_USER_VIEW = "user/add";
    private static final String ADD_USER_ERROR_REDIRECT_VIEW = "redirect:/user/add?errorMsg=";

    private static final String EDIT_USER_VIEW = "user/edit";
    private static final String EDIT_USER_REDIRECT_VIEW = "redirect:/user/edit/";

    private static final String ERROR_MSG_PARAM = "?errorMsg=";

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<UserEntity> userList = userService.getAllUsers();
		
		model.put("userList", userList);
		return LIST_USER_VIEW;
	}

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addForm(ModelMap model) {
        model.put("userCommand", prepareUserCommand(null,null,Role.ROLE_USER,null, prepareRoleMap(false,true)) );
        return ADD_USER_VIEW;
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
            return LIST_USER_SUCCESS_REDIRECT_VIEW + succMsg;
        } else {
            String errorMsg = "User creation fails already same user name exists please try "
                    + "with different user name";
            model.addAttribute("errorMsg", errorMsg);
            return ADD_USER_ERROR_REDIRECT_VIEW + errorMsg;
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
            return LIST_USER_REDIRECT_VIEW;
        }
        return LIST_USER_ERROR_REDIRECT_VIEW + errorMsg;
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public String editForm(ModelMap model,
       @PathVariable Long id){

        UserEntity user = userService.getUserById(id);

        if(user == null){
            String errorMsg = "No such user";
            model.addAttribute("errorMsg", errorMsg);
            return LIST_USER_ERROR_REDIRECT_VIEW + errorMsg;
        }

        model.put("userCommand",
                prepareUserCommand(
                        user,
                        null,
                        user.getRole().getName(),
                        null,
                        prepareRoleMap(false,true)));
        return EDIT_USER_VIEW;
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
            return EDIT_USER_REDIRECT_VIEW + id + ERROR_MSG_PARAM + errorMsg;
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
            return EDIT_USER_REDIRECT_VIEW + id + ERROR_MSG_PARAM + errorMsg;
        }
        String succMsg = "Tenant successfully edited ";
        return LIST_USER_SUCCESS_REDIRECT_VIEW + succMsg;
    }
}
