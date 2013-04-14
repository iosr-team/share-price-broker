package pl.edu.agh.iosr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

public abstract class TenantResolver {
	
	@Autowired private UserService userService;
	
	public Tenant resolveTenant(){
	    return resolveUser().getTenant();
	}
	
	public UserEntity resolveUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    UserEntity user = userService.getUserByLogin(name);
	    return user;
	}
}
