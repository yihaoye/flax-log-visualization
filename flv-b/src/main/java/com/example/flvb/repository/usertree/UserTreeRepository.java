package com.example.flvb.dao.usertree;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.flvb.model.usertree.*;

public interface UserTreeRepository extends MongoRepository<User, String> {
    public User findByUserID(String userID);
    public List<User> findAll();
}