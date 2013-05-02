package pl.edu.agh.iosr.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.services.UserService;

@Service("userDetailsService") 
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
    @Qualifier("userService")
    private UserService userService;

	@Autowired
    private UserAssembler userAssembler;

  	@Transactional(readOnly = true)
  	public UserDetails loadUserByUsername(String username)
  			throws UsernameNotFoundException, DataAccessException {
  		log.debug("login = "+username);

  		UserEntity userEntity = userService.getUserByLogin(username);

  		if (userEntity == null){
  			log.info("userEntity is null");
  			throw new UsernameNotFoundException("user not found");
  		}

  		return userAssembler.buildUserFromUserEntity(userEntity);
  	}
}
