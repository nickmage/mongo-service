package com.mongo.mongoservice.repositories;

import com.mongo.mongoservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {



}
