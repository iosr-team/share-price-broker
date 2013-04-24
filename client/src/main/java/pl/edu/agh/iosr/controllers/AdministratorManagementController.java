package pl.edu.agh.iosr.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.services.UserService;

@Controller
public class AdministratorManagementController {
	
	private final Logger log = LoggerFactory.getLogger(AdministratorManagementController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<UserEntity> administratorList = userService.getAllUsersOfRole("ROLE_ADMIN");
		
		model.put("administratorList", administratorList);
		return "administrator/list";
	}
	
	@RequestMapping(value = "/administrator/edit/{id}", method = RequestMethod.GET)
	public String edit(ModelMap model,
			@PathVariable Long id){
		
		UserEntity administrator = userService.getUserById(id);
		model.put("administrator", administrator);
		return "administrator/edit";
	}
}
