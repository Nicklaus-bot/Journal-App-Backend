package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.user;
import net.engineeringdigest.journalApp.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping
    public List<user> getAll(){
        return userService.getAll();
    }

    @PostMapping
    public void save(@RequestBody user user){
        userService.save(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<user> update(@RequestBody user user , @PathVariable String username){
        user userDB = userService.findBy(username);
        if(userDB != null){
            userDB.setUsername(user.getUsername());
            userDB.setPassword(user.getPassword());
            userService.save(userDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
