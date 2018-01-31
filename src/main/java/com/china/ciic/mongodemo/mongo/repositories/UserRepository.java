package com.china.ciic.mongodemo.mongo.repositories;

import com.china.ciic.mongodemo.mongo.po.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * user持久层
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {


}
