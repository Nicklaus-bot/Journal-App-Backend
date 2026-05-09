package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.entryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class entryService {
    @Autowired
    private entryRepository entryRepository;

    public void saveEntry(JournalEntry journalEntry){
        entryRepository.save(journalEntry);
    }

    public List<JournalEntry> showEntries(){
        return entryRepository.findAll();
    }

    public Optional <JournalEntry> findId(ObjectId id){
        return entryRepository.findById(id);
    }

    public void delete(ObjectId id){
        entryRepository.deleteById(id);
    }

}
