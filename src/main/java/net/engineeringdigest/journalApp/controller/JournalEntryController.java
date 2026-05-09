package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.services.entryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private entryService entryService;


    @GetMapping
    public List<JournalEntry> allEntries() {
        return entryService.showEntries();
    }

    @PostMapping
    public JournalEntry saveEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        entryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{myID}")
    public JournalEntry getEntry(@PathVariable ObjectId myID){
        return entryService.findId(myID).orElse(null);
    }

    @DeleteMapping("/delete/{myID}")
    public String deleteID(@PathVariable ObjectId myID){
        entryService.delete(myID);
        return "ID_Data_Deleted";
    }

    @PutMapping("/updateid/{myID}")
    public JournalEntry update(@PathVariable ObjectId myID , @RequestBody JournalEntry newEntry ){
        JournalEntry oldEntry = entryService.findId(myID).orElse(null);
        if(oldEntry != null){
            if(newEntry.gettitle() != null){
                oldEntry.settitle(newEntry.gettitle());
            }
            if(newEntry.getcontent() != null){
                oldEntry.setcontent(newEntry.getcontent());
            }
            saveEntry(oldEntry);
        }
        return oldEntry;
    }
}
