package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.model.Group;

public interface GroupRepository extends MongoRepository<Group,String> {
		List<Group> findByadminEmail(String Email);
		
}
