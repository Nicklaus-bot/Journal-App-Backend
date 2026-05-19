package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.services.entryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private entryService entryService;


    @GetMapping
    public ResponseEntity<?> allEntries() {
        List<JournalEntry> all = entryService.showEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry myEntry) {
        try{
            myEntry.setDate(LocalDateTime.now());
            entryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myID}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId myID){
        Optional<JournalEntry> entry = entryService.findId(myID);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get() , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{myID}")
    public ResponseEntity<?> deleteID(@PathVariable ObjectId myID){
        entryService.delete(myID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateid/{myID}")
    public ResponseEntity<?> update(@PathVariable ObjectId myID , @RequestBody JournalEntry newEntry ){
        JournalEntry oldEntry = entryService.findId(myID).orElse(null);
        if(oldEntry != null){
            if(newEntry.gettitle() != null){
                oldEntry.settitle(newEntry.gettitle());
            }
            if(newEntry.getcontent() != null){
                oldEntry.setcontent(newEntry.getcontent());
            }
            saveEntry(oldEntry);

            return new ResponseEntity<>(oldEntry , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
