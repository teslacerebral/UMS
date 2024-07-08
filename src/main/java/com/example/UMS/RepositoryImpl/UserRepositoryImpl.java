package com.example.UMS.RepositoryImpl;

import com.example.UMS.Model.User;
import com.example.UMS.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryImpl {

    @Autowired
    UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @PersistenceContext
    private EntityManager entityManager;


    public boolean existsByUsername(String username) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }
}
