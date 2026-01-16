package com.reasonly.backend.QuestionAttempt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.User.User;

public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
    List<QuestionAttempt> findByUser(User user);
    List<QuestionAttempt> findByQuestion(Question question);
    List<QuestionAttempt> findByUserAndQuestion(User user, Question question);
}