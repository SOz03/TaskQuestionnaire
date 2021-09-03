package ru.code.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.code.task.data.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
}
