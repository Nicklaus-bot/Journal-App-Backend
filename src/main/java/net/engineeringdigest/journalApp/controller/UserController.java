package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import static net.engineeringdigest.journalApp.services.UserService.passwordEncoder;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<?> greet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherResponse response = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(response != null){
            greeting = ", Today feels like " + response.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+ username + greeting , HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userDB = userService.findByUsername(username);
            userDB.setUsername(user.getUsername());
            userDB.setPassword(user.getPassword());
            userService.saveNewUser(userDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<User> delete(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
