package pl.edu.agh.iosr.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userAssembler")
public class UserAssembler {

  @Transactional(readOnly = true)
  User buildUserFromUserEntity(pl.edu.agh.iosr.model.User userEntity) {

    String username = userEntity.getLogin();
    String password = userEntity.getPassword();
    boolean enabled = userEntity.isEnabled();
    boolean accountNonExpired = userEntity.isEnabled();
    boolean credentialsNonExpired = userEntity.isEnabled();
    boolean accountNonLocked = userEntity.isEnabled();

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new GrantedAuthorityImpl(userEntity.getRole().getName()));


    User user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities); 
    
    return user;
  }
}
