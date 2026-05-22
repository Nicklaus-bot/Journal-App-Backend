package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository entryRepository;

    @Autowired
    private UserService userService;

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
        user.getEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        entryRepository.deleteById(id);
    }

}
