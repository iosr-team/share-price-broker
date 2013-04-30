package pl.edu.agh.iosr.services.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.model.security.CustomUserDetails;

@Service("userAssembler")
public class UserAssembler{
	private final Logger log = LoggerFactory.getLogger(UserAssembler.class);
	
	@Transactional(readOnly = true)
	User buildUserFromUserEntity(UserEntity userEntity) {
		log.debug("login = "+userEntity.getLogin());
		
	    String username = userEntity.getLogin();
	    String password = userEntity.getPassword();
	    boolean enabled = userEntity.isEnabled() && userEntity.getTenant().isEnabled(); // user and tenant have to be enabled
	    boolean accountNonExpired = userEntity.isEnabled();
	    boolean credentialsNonExpired = userEntity.isEnabled();
	    boolean accountNonLocked = userEntity.isEnabled();
	
	    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    authorities.add(new GrantedAuthorityImpl(userEntity.getRole().getName()));
	
	    CustomUserDetails user = new CustomUserDetails(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities); 
	    user.setUser(userEntity);
	    return user;
	}
}
