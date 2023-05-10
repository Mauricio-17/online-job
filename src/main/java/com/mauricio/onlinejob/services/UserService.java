package com.mauricio.onlinejob.services;

import com.mauricio.onlinejob.dto.UserDtoRequest;
import com.mauricio.onlinejob.entities.User;
import com.mauricio.onlinejob.entities.UserType;
import com.mauricio.onlinejob.exceptions.BadRequestException;
import com.mauricio.onlinejob.exceptions.NotFoundException;
import com.mauricio.onlinejob.repositories.UserRepository;
import com.mauricio.onlinejob.repositories.UserTypeRepository;
import com.mauricio.onlinejob.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserTypeRepository userTypeRepository;

    public record AuthenticationRequest (String username, String password){}
    public record AuthenticationResponse (String token){}

    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public UserDtoRequest getUserByUsername(String username){
        Optional<User> userFound = userRepository.findByUsername(username);
        if (userFound.isEmpty()){
            throw new NotFoundException(String.format("Usuario %s no encontrado!",
                    username));
        }
        return entityToDto.apply(userFound.get());
    }

    public List<UserDtoRequest> getAllUsers() {
        return userRepository.findAll().stream().map(entityToDto).collect(Collectors.toList());
    }

    public AuthenticationResponse registerUser(UserDtoRequest request) {
        Optional<User> userFound = userRepository.findByUsername(request.username());
        if (userFound.isPresent()){
            throw new BadRequestException(String.format("El nombre de usuario %s ya " +
                    "existe", userFound.get().getUsername()));
        }
        Optional<UserType> userType = userTypeRepository.findById(request.userTypeId());
        if(userType.isEmpty()) {
            throw new NotFoundException(String.format("Role with id %d not found", request.userTypeId()));
        }
        User user = User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .birthday( LocalDate.parse(request.birthday(), dateFormatter))
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userType(userType.get()).build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        );
        var user = userRepository.findByUsername(authRequest.username)
                .orElseThrow(() ->
                        new NotFoundException(String.format("El usuario %s no fue " +
                                "encontrado", authRequest.username()))
                );
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public Long getAmountOfUsers() {
        return userRepository.count();
    }

    public UserDtoRequest updateUser(Long id, UserDtoRequest user) {
        User userUpdated = userRepository.findById(id).map(item -> {
            item.setName(user.name());
            item.setLastName(user.lastName());
            item.setBirthday(LocalDate.parse(user.birthday(), dateFormatter));
            item.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(item);
        }).orElseThrow(() -> new NotFoundException(String.format("El usuario %s no fue " +
                "encontrado", user.username())));

        return entityToDto.apply(userUpdated);
    }

    public void deleteUser(Long id) {
        User userFound =
                userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("ID %d no encontrado.", id)));
        userRepository.delete(userFound);
    }

    private final Function<User, UserDtoRequest> entityToDto = user ->
            new UserDtoRequest(
                    user.getName(),
                    user.getLastName(),
                    user.getBirthday().format(dateFormatter),
                    user.getUsername(),
                    user.getPassword(),
                    user.getCreatedAt().format(dateTimeFormatter),
                    user.getUpdatedAt().format(dateTimeFormatter),
                    user.getUserType().getId()
            );


}


