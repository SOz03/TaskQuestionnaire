package ru.code.task.data.repository;

import org.springframework.stereotype.Repository;
import ru.code.task.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}