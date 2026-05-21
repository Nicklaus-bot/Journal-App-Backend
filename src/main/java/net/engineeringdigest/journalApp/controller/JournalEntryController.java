package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<?> allEntries(@PathVariable String username) {
        User user = userService.findBy(username);
        List<JournalEntry> all = user.getEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry myEntry , @PathVariable String username) {
        try{
            entryService.saveEntry(myEntry , username);
            return new ResponseEntity<>(myEntry , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{myID}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId myID){
        Optional<JournalEntry> entry = entryService.findId(myID);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get() , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}/{myID}")
    public ResponseEntity<?> deleteID(@PathVariable String username , @PathVariable ObjectId myID){
        entryService.delete(myID , username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{username}/{myID}")
    public ResponseEntity<?> update(@PathVariable String username , @PathVariable ObjectId myID , @RequestBody JournalEntry newEntry ){
        JournalEntry oldEntry = entryService.findId(myID).orElse(null);
        if(oldEntry != null){
            if(newEntry.getTitle() != null){
                oldEntry.setTitle(newEntry.getTitle());
            }
            if(newEntry.getContent() != null){
                oldEntry.setContent(newEntry.getContent());
            }
            entryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
