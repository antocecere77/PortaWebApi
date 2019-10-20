package com.portal.webapp.repository;

import com.portal.webapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UtentiRepository extends MongoRepository<User, String> {
    public User findByUserId(String UserId);
}
