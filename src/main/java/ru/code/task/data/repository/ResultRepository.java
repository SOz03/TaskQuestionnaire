package ru.code.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.code.task.data.entity.Result;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, String> {
    @Query("SELECT r.nameQuestionnaire FROM Result r WHERE r.id = ?1")
    String findNameQuestionnaireById(String id);

    @Query("SELECT r FROM Result r WHERE r.nameQuestionnaire = ?1")
    List<Result> findResultsByNameQuestionnaire(String nameQuestionnaire);
}
