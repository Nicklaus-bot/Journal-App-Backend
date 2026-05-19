package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<user, ObjectId> {
    public user findBy(String Username);
}
