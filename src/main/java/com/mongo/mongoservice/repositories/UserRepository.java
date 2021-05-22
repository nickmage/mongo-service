package com.mongo.mongoservice.repositories;

import com.mongo.mongoservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{followings: ?0}")
    Set<User> getFollowersById(String userId);

}
