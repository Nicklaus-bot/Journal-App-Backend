package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long , JournalEntry> jEntry = new HashMap<>();

    @GetMapping
    public ArrayList<JournalEntry> JournalEntry() {
        return new ArrayList<>(jEntry.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        jEntry.put(myEntry.getid() , myEntry);
        return true;
    }

    @GetMapping("/id/{myID}")
    public JournalEntry getEntry(@PathVariable Long myID){
        return jEntry.get(myID);
    }

    @DeleteMapping("/delete/{myID}")
    public JournalEntry deleteID(@PathVariable Long myID){
        return jEntry.remove(myID);
    }

    @PutMapping("/updateid/{myID}")
    public JournalEntry update(@PathVariable Long myID , @RequestBody JournalEntry myEntry ){
        return jEntry.put(myID , myEntry);
    }

}
