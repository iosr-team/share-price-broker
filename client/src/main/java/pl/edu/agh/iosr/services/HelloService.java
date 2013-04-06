package pl.edu.agh.iosr.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author bputo
 * This is a quite dummy service. 
 * TODO: remove it later.
 */
@Service
public class HelloService {

	public String getHelloName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    return name.toUpperCase();
	}
	
}
