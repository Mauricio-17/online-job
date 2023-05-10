package com.mauricio.onlinejob.controllers;

import com.mauricio.onlinejob.dto.UserDtoRequest;
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
    public List<UserDtoRequest> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/amount")
    public Long getAmountOfUsers(){
        return userService.getAmountOfUsers();
    }

    /*
    @PostMapping
    public ResponseEntity<UserDtoRequest> saveUser(@Valid @RequestBody UserDtoRequest userDtoRequest){
        UserDtoRequest user = userService.saveUser(userDtoRequest);
        return ResponseEntity.status(201).body(user);
    }
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDtoRequest> updateUser(@PathVariable(value = "id") Long fileId, UserDtoRequest userDto){
        UserDtoRequest userResponse = userService.updateUser(fileId, userDto);
        return ResponseEntity.status(202).body(userResponse);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }
}
