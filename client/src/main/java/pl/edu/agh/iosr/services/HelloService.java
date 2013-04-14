package pl.edu.agh.iosr.services;

import org.springframework.stereotype.Service;

import pl.edu.agh.iosr.model.entity.UserEntity;

/**
 * 
 * @author bputo
 * This is a quite dummy service. 
 * TODO: remove it later.
 */
@Service
public class HelloService extends TenantResolver {

	public String getHelloName(){
	    UserEntity user = this.resolveUser();
	    return "User "+user.getLogin()+" ["+user.getTenant().getName()+"]";
	}
	
}
