package pl.edu.agh.iosr.model.security;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

public class CustomUserDetails extends User{
	
	private UserEntity user;
	
	public CustomUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}

	public Tenant getTenant() {
		return this.user.getTenant();
	}
	
	public UserEntity getUser(){
		return this.user;
	}
	
	public void setUser(UserEntity user){
		this.user = user;
	}
}
