package pl.edu.agh.iosr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.User;

@Service("userDetailsService") 
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserService userService;
  @Autowired private UserAssembler userAssembler;

  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException, DataAccessException {

    UserDetails userDetails = null;
    User userEntity = userService.getUserByUserName(username);
    if (userEntity == null){
    	throw new UsernameNotFoundException("user not found");
    }

    return userAssembler.buildUserFromUserEntity(userEntity);
  }
}
