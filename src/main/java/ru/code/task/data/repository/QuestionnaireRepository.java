package ru.code.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.code.task.data.entity.Question;
import ru.code.task.data.entity.Questionnaire;
import java.util.List;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, String> {
    @Query("SELECT q.questions FROM Questionnaire q WHERE q.id = ?1")
    List<Question> findListByIdQuestionnaire(String id);
}
