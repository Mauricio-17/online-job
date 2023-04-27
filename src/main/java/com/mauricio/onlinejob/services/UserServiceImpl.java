package com.mauricio.onlinejob.services;

import com.mauricio.onlinejob.dto.JobDto;
import com.mauricio.onlinejob.dto.UserDto;
import com.mauricio.onlinejob.entities.User;
import com.mauricio.onlinejob.entities.UserType;
import com.mauricio.onlinejob.exceptions.BadRequestException;
import com.mauricio.onlinejob.exceptions.NotFoundException;
import com.mauricio.onlinejob.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public UserDto getUserByUsername(String username){
        Optional<User> userFound = userRepository.findByUsername(username);
        if (userFound.isEmpty()){
            throw new NotFoundException(String.format("Usuario %s no encontrado!",
                    username));
        }
        return entityToDto.apply(userFound.get());
    }

    @Override public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(entityToDto).collect(Collectors.toList());
    }

    @Override public UserDto saveUser(UserDto userDto) {
        Optional<User> userFound = userRepository.findByUsername(userDto.username());
        if (userFound.isPresent()){
            throw new BadRequestException(String.format("El nombre de usuario %s ya " +
                    "existe", userFound.get().getUsername()));
        }
        String passwordEncrypted = "·$1dp32'$da009jddj0Ñe30";
        UserType userType = UserType.builder().id(userDto.userTypeId()).build();
        User user = User.builder()
                .name(userDto.name())
                .lastName(userDto.lastName())
                .birthday( LocalDate.parse(userDto.birthday(), dateFormatter))
                .username(userDto.username())
                .password(passwordEncrypted)
                .userType(userType).build();
        User userSaved = userRepository.save(user);
        return entityToDto.apply(userSaved);
    }

    @Override public long getAmountOfUsers() {
        return userRepository.count();
    }

    @Override public UserDto updateUser(Long id, UserDto user) {
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

    @Override public void deleteUser(Long id) {
        User userFound =
                userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("ID %d no encontrado.", id)));
        userRepository.delete(userFound);
    }

    private final Function<User, UserDto> entityToDto = user ->
            new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getLastName(),
                    user.getBirthday().format(dateFormatter),
                    user.getUsername(),
                    user.getCreatedAt().format(dateTimeFormatter),
                    user.getUpdatedAt().format(dateTimeFormatter),
                    user.getUserType().getId()
            );


}


