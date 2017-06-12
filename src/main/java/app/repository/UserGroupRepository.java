package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.model.UserGroup;

public interface UserGroupRepository extends MongoRepository<UserGroup, String> {
List<UserGroup> findByuserEmail(String Email);
UserGroup findByuserEmailAndGroupName(String Email, String groupname);
List<UserGroup> findBygroupName(String Email);
}
