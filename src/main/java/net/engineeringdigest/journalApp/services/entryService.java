package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.user;
import net.engineeringdigest.journalApp.repository.entryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class entryService {
    @Autowired
    private entryRepository entryRepository;

    @Autowired
    private userService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry , String username){
        user user = userService.findBy(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = entryRepository.save(journalEntry);
        user.getList().add(saved);
        userService.save(user);
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
        user user = userService.findBy(username);
        user.getList().removeIf(x -> x.getId().equals(id));
        userService.save(user);
        entryRepository.deleteById(id);
    }

}
