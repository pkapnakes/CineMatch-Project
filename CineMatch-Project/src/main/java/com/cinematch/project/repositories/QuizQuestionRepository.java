package com.cinematch.project.repositories;

import com.cinematch.project.models.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    // MySQL: ORDER BY RAND()
    @Query(value = "SELECT * FROM quiz_questions WHERE genre = :genre ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<QuizQuestion> findRandomFiveByGenre(@Param("genre") String genre);
}
