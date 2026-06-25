package com.reasonly.backend.Question;

import java.util.List;
import java.util.Random;

import com.reasonly.backend.Question.CodeWriting.CodeExecutionResult;
import com.reasonly.backend.Question.CodeWriting.CodeExecutionService;
import com.reasonly.backend.Question.CodeWriting.CodeRunningRequest;
import com.reasonly.backend.User.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final Random random;
    private final CodeExecutionService codeExecutionService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, CodeExecutionService codeExecutionService) {
        this(questionRepository, new Random(), codeExecutionService);
    }

    public QuestionService(QuestionRepository questionRepository, Random random, CodeExecutionService codeExecutionService) {
        this.questionRepository = questionRepository;
        this.random = random;
        this.codeExecutionService = codeExecutionService;
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Question not found with id: " + id));
    }

    public List<Question> getPlayQuestions(User user) {
        int rating = user.getRating();
        QuestionDifficulty targetDifficulty = getDifficultyFromRating(rating);
        QuestionDifficulty selectedDifficulty = selectProbabilisticDifficulty(targetDifficulty);
        
        if (Math.random() < 0.5) {
            Question result = getReviewQuestion(user);
            if (result != null) {
                return List.of(result);
            }
        }

        com.reasonly.backend.User.UserSettings.UserLanguage userLanguage = user.getUserSettings() != null ? user.getUserSettings().getPreferredLanguage() : null;

        QuestionTopic questionTopic = getQuestionTopic(user);
        List<Question> potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserIdAndQuestionTopicAndLanguage(selectedDifficulty,
                user.getId(), questionTopic, userLanguage);

        if (!potentialQuestions.isEmpty()) {
            java.util.Collections.shuffle(potentialQuestions);
            return List.of(potentialQuestions.get(0));
        }

        // Fallback 1: Get any question topic in selectedDifficulty
        potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserIdAndLanguage(selectedDifficulty, user.getId(), userLanguage);

        if (!potentialQuestions.isEmpty()) {
            java.util.Collections.shuffle(potentialQuestions);
            return List.of(potentialQuestions.get(0));
        }

        // Fallback 2: Try exact target difficulty if we diverted
        if (selectedDifficulty != targetDifficulty) {
            potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserIdAndLanguage(targetDifficulty, user.getId(), userLanguage);
            if (!potentialQuestions.isEmpty()) {
                java.util.Collections.shuffle(potentialQuestions);
                return List.of(potentialQuestions.get(0));
            }
        }

        // Fallback 3: Try any difficulty, prioritizing closest to target
        for (QuestionDifficulty d : QuestionDifficulty.values()) {
            if (d == selectedDifficulty || d == targetDifficulty)
                continue;
            potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserIdAndLanguage(d, user.getId(), userLanguage);
            if (!potentialQuestions.isEmpty()) {
                java.util.Collections.shuffle(potentialQuestions);
                return List.of(potentialQuestions.get(0));
            }
        }
        // If no unanswered questions left, give review question. If no review question,
        // return empty list.
        Question result = getReviewQuestion(user);
        if (result != null) {
            return List.of(result);
        }
        return List.of();
    }


    // Preferred questions selected by the user are 2x more likely to show up
    private QuestionTopic getQuestionTopic(User user) {
        List<QuestionTopic> interests = user.getUserSettings() != null && user.getUserSettings().getInterests() != null ? user.getUserSettings().getInterests() : List.of();
        int totalSlots = QuestionTopic.values().length + interests.size(); 
        QuestionTopic[] topics = new QuestionTopic[totalSlots];
        int currentIndex = 0;
        
        for (QuestionTopic topic : QuestionTopic.values()) {
            topics[currentIndex++] = topic;
            
            // If the user is interested in this topic, add it a second time
            if (interests.contains(topic)) {
                topics[currentIndex++] = topic;
            }
        }
        
        int randomIndex = random.nextInt(totalSlots);
        return topics[randomIndex];
    }

    private QuestionDifficulty getDifficultyFromRating(int rating) {
        if (rating <= 1000)
            return QuestionDifficulty.BASIC;
        if (rating <= 2000)
            return QuestionDifficulty.EASY;
        if (rating <= 3000)
            return QuestionDifficulty.MEDIUM;
        if (rating <= 4000)
            return QuestionDifficulty.HARD;
        return QuestionDifficulty.EXTREME;
    }

    private QuestionDifficulty selectProbabilisticDifficulty(QuestionDifficulty target) {
        int roll = (int) (random.nextDouble() * 100);

        // 70% chance for target difficulty
        if (roll < 70) {
            return target;
        }

        // 15% chance for easier (if possible)
        if (roll < 85) {
            return getEasierDifficulty(target);
        }

        // 15% chance for harder (if possible)
        return getHarderDifficulty(target);
    }

    private QuestionDifficulty getEasierDifficulty(QuestionDifficulty current) {
        int val = current.getValue() - 1;
        if (val < 1)
            return current; // already lowest
        return getDifficultyByValue(val);
    }

    private QuestionDifficulty getHarderDifficulty(QuestionDifficulty current) {
        int val = current.getValue() + 1;
        if (val > 5)
            return current; // already highest
        return getDifficultyByValue(val);
    }

    private QuestionDifficulty getDifficultyByValue(int value) {
        for (QuestionDifficulty d : QuestionDifficulty.values()) {
            if (d.getValue() == value)
                return d;
        }
        return QuestionDifficulty.BASIC;
    }

    private Question getReviewQuestion(User user) {
        return questionRepository.findRandomDueReview(user.getId()).orElse(null);
    }

    public CodeExecutionResult runCode(CodeRunningRequest request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + request.getQuestionId()));
        return codeExecutionService.executeCode(request.getUserCode(), question.getSampleTestCases(),
                question.getSampleExpectedOutputs(), question.getMethodName(), request.getLanguage());
    }
}