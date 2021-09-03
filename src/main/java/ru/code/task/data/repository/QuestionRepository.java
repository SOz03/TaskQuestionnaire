package ru.code.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.code.task.data.entity.Answer;
import ru.code.task.data.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    @Query("SELECT q.listAnswer FROM Question q WHERE q.id = ?1")
    List<Answer> findListByIdQuestion(String id);


}
