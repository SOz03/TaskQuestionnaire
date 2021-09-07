package ru.code.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.code.task.data.entity.ResultAnswer;

@Repository
public interface ResultAnswerRepository extends JpaRepository<ResultAnswer, String> {
}
