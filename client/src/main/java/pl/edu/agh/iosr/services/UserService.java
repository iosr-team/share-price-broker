package pl.edu.agh.iosr.services;

import pl.edu.agh.iosr.model.entity.UserEntity;

public interface UserService {
    UserEntity getUserById(Long id);
    UserEntity getUserByLogin(String userName);
    UserEntity createUser(UserEntity user);
}

