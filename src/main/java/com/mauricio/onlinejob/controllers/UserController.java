package com.mauricio.onlinejob.controllers;

import com.mauricio.onlinejob.dto.UserDto;
import com.mauricio.onlinejob.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.saveUser(userDto);
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping(value = "/{fileId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(value = "fileId") Long fileId, UserDto userDto){
        UserDto userResponse = userService.updateUser(fileId, userDto);
        return ResponseEntity.status(202).body(userResponse);
    }


}
