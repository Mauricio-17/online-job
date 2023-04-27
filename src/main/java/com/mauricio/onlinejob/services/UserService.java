package com.mauricio.onlinejob.services;

import com.mauricio.onlinejob.dto.UserDto;
import com.mauricio.onlinejob.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto getUserByUsername(String username);

    List<UserDto> getAllUsers();
    UserDto saveUser(UserDto user);
    long getAmountOfUsers();
    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);

}
