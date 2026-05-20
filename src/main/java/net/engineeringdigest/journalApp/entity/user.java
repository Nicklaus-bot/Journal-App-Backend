package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class user {
    @Id
    private ObjectId Id;

    @Indexed(unique = true)
    @NonNull
    private String Username;

    @NonNull
    private String Password;

    @DBRef
    private List<JournalEntry> List = new ArrayList<>();
}
