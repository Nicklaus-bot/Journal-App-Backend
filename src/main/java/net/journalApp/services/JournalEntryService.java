package net.journalApp.services;

import net.journalApp.entity.JournalEntry;
import net.journalApp.entity.User;
import net.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository entryRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry , String username){
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = entryRepository.save(journalEntry);
        user.getEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        entryRepository.save(journalEntry);
    }

    public List<JournalEntry> showEntries(){
        return entryRepository.findAll();
    }

    public Optional <JournalEntry> findId(ObjectId id){
        return entryRepository.findById(id);
    }

    @Transactional
    public void delete(ObjectId id , String username){
        User user = userService.findByUsername(username);
        boolean remove = user.getEntries().removeIf(x -> x.getId().equals(id));
        if(remove){
            userService.saveUser(user);
            entryRepository.deleteById(id);
        }
    }
}
