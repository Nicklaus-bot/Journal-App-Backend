package net.journalApp.controller;

import net.journalApp.entity.JournalEntry;
import net.journalApp.entity.User;
import net.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String HealthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> save(@RequestBody User user){
        try{
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
