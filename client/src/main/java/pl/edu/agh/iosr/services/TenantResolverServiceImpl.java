package pl.edu.agh.iosr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

@Service
public class TenantResolverServiceImpl implements TenantResolverService {

    @Autowired
    @Qualifier("userService")
    private UserService userService;
	
	public Tenant resolveTenant(){
	    return resolveUser().getTenant();
	}
	
	public UserEntity resolveUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    UserEntity user = userService.getUserByLogin(name);
	    return user;
	}

    @Override
    public Boolean canModify(Tenant tenant) {
        if(tenant == null){
            return true;
        }

        UserEntity myUser = resolveUser();

        if(myUser.getTenant().isSuperuser()){
            return true;
        }
        if(myUser.getTenant().getName().equals(tenant.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean canModify(UserEntity userEntity) {
        if(userEntity == null){
            return true;
        }
        return canModify(userEntity.getTenant());
    }
}
