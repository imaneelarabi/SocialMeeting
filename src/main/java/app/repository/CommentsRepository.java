package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.model.Comments;

public interface CommentsRepository extends MongoRepository<Comments, String> {
List<Comments> findBygroupName(String groupname);
}
