package pl.edu.agh.iosr.services;

import pl.edu.agh.iosr.model.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByUserName(String userName);
    User createUser(User user);
}

