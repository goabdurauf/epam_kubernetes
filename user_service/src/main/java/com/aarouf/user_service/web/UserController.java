package com.aarouf.user_service.web;

import com.aarouf.user_service.domain.User;
import com.aarouf.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return new UserDto(userRepository.save(new User(userDto.getUsername())));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("id") long id) {
        User user = verifyUser(id);
        return new UserDto(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("id") long id) {
        User user = verifyUser(id);
        userRepository.delete(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(
            @PathVariable("id") long id,
            @RequestBody UserDto userDto
    ) {
        User user = verifyUser(id);
        user.setUsername(userDto.getUsername());
        user.setAmountOfPosts(userDto.getAmountOfPosts());
        return new UserDto(userRepository.save(user));
    }


    private User verifyUser(long userId) throws NoSuchElementException {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new NoSuchElementException("Client does not found");
        });
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}
