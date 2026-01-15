package com.cinematch.project.repositories;

import com.cinematch.project.models.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByUserIdOrderByCreatedAtDesc(Long userId);
    QuizAttempt findTopByUserIdOrderByCreatedAtDesc(Long userId);

}
