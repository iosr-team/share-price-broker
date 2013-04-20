package pl.edu.agh.iosr.services;

import java.util.List;

import pl.edu.agh.iosr.model.entity.UserEntity;

public interface UserService {
    UserEntity getUserById(Long id);
    UserEntity getUserByLogin(String userName);
    UserEntity createUser(UserEntity user);
    List<UserEntity> getAllUsersOfRole(String roleName);
}

