package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.user;
import net.engineeringdigest.journalApp.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class userService {
    @Autowired
    private userRepository userRepository;

    public List<user> getAll(){
        return userRepository.findAll();
    }

    public void save(user User){
        userRepository.save(User);
    }

    public user findBy(String Username){
        return userRepository.findBy(Username);
    }

}
