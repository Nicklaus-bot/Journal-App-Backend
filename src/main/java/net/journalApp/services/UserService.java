package net.journalApp.services;

import net.journalApp.entity.User;
import net.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("Users"));
            userRepository.save(user);
        }
        catch(Exception e){
            logger.info("Info Error");
            logger.error("Error occurred for {} :" , user.getUsername() , e);
            logger.warn("Warning");
            logger.debug("Debug");
            logger.trace("Trace");
        }
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    public void saveAdmin(User user){
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("Users" , "ADMIN"));
        userRepository.save(user);
    }

}
