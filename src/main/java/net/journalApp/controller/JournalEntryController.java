package net.journalApp.controller;

import net.journalApp.entity.JournalEntry;
import net.journalApp.entity.User;
import net.journalApp.services.JournalEntryService;
import net.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> allEntries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> all = user.getEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry myEntry) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            entryService.saveEntry(myEntry , username);
            return new ResponseEntity<>(myEntry , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myID}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getEntries().stream().filter(x -> x.getId().equals(myID))
                                    .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> entry = entryService.findId(myID);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myID}")
    public ResponseEntity<?> deleteID(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        entryService.delete(myID , username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{myID}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId myID , @RequestBody JournalEntry newEntry ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getEntries().stream().filter(x -> x.getId().equals(myID))
                .collect(Collectors.toList());

        if(!collect.isEmpty()) {
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
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
