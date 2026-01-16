package com.reasonly.backend;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(QuestionRepository repository) {
        return args -> {
            List<Question> questions = List.of(
                new Question(null, QuestionType.MATH, QuestionDifficulty.BASIC, 
                    "What is the next prime number after 7?", 
                    List.of("9", "11", "13", "15"), "11"),

                new Question(null, QuestionType.LOGIC, QuestionDifficulty.MEDIUM, 
                    "If all Bloops are Razzies and all Razzies are Lazzies, are all Bloops definitely Lazzies?", 
                    List.of("Yes", "No", "Only on Tuesdays", "Cannot be determined"), 
                    "Yes"),

                new Question(null, QuestionType.PATTERNS, QuestionDifficulty.EASY, 
                    "Complete the sequence: 2, 6, 18, 54, ...", 
                    List.of("108", "144", "162", "216"), 
                    "162"),

                new Question(null, QuestionType.ALGORITHMIC, QuestionDifficulty.HARD, 
                    "A robot follows these rules: Move Forward 1, Turn Right 90°. If it repeats this 4 times, where does it end up?", 
                    List.of("1 unit North", "1 unit East", "At the starting point", "4 units West"), 
                    "At the starting point"),

                new Question(null, QuestionType.VERBAL, QuestionDifficulty.BASIC, 
                    "Hand is to Glove as Foot is to:", 
                    List.of("Shoe", "Hat", "Sock", "Sandal"), 
                    "Sock"),

                new Question(null, QuestionType.SPATIAL, QuestionDifficulty.EXTREME, 
                    "A transparent cube has a red dot on the top face and a blue dot on the bottom face. If you rotate the cube 180 degrees forward, where is the red dot?", 
                    List.of("Top", "Bottom", "Front", "Back"), 
                    "Bottom"),

                new Question(null, QuestionType.CRITICAL, QuestionDifficulty.MEDIUM, 
                    "Identify the fallacy: 'My grandfather smoked 2 packs a day and lived to be 100, so smoking isn't bad for you.'", 
                    List.of("Slippery Slope", "Ad Hominem", "Anecdotal Evidence", "Strawman"), 
                    "Anecdotal Evidence"),

                new Question(null, QuestionType.LOGIC, QuestionDifficulty.BASIC, 
                    "If all squares are rectangles, and all rectangles are shapes, are all squares shapes?", 
                    List.of("Yes", "No", "Sometimes", "Only if they are equal"), 
                    "Yes"),

                new Question(null, QuestionType.MATH, QuestionDifficulty.EASY, 
                    "What is the missing number in the sequence: 1, 4, 9, 16, ?, 36", 
                    List.of("20", "24", "25", "30"), 
                    "25"),

                new Question(null, QuestionType.VERBAL, QuestionDifficulty.MEDIUM, 
                    "Acoustic is to Guitar as Electric is to:", 
                    List.of("Piano", "Drums", "Amplifier", "Keyboard"), 
                    "Keyboard"),

                new Question(null, QuestionType.PATTERNS, QuestionDifficulty.MEDIUM, 
                    "Which comes next in the sequence: O, T, T, F, F, S, S, E, ?", 
                    List.of("N", "T", "E", "S"), 
                    "N"), // One, Two, Three, Four, Five, Six, Seven, Eight, NINE

                new Question(null, QuestionType.ALGORITHMIC, QuestionDifficulty.EASY, 
                    "If x = 5 and y = 10, then x = y and y = x. What is the value of x now?", 
                    List.of("5", "10", "15", "0"), 
                    "10"),

                new Question(null, QuestionType.SPATIAL, QuestionDifficulty.MEDIUM, 
                    "If you rotate the letter 'L' 90 degrees clockwise and then flip it vertically, which way does the short end point?", 
                    List.of("Up", "Down", "Left", "Right"), 
                    "Up"),

                new Question(null, QuestionType.CRITICAL, QuestionDifficulty.HARD, 
                    "Which is a logical fallacy where an argument is rebutted by attacking the character of the person making it?", 
                    List.of("Slippery Slope", "Ad Hominem", "Red Herring", "Circular Reasoning"), 
                    "Ad Hominem"),

                new Question(null, QuestionType.MATH, QuestionDifficulty.EXTREME, 
                    "A bat and a ball cost $1.10 in total. The bat costs $1.00 more than the ball. How much does the ball cost?", 
                    List.of("$0.10", "$0.05", "$0.01", "$0.15"), 
                    "$0.05"),

                new Question(null, QuestionType.LOGIC, QuestionDifficulty.HARD, 
                    "Five people are in a race. You pass the person in second place. What place are you in now?", 
                    List.of("First", "Second", "Third", "Fourth"), 
                    "Second"),

                new Question(null, QuestionType.VERBAL, QuestionDifficulty.EXTREME, 
                    "What is the antonym of 'Ephemeral'?", 
                    List.of("Fleeting", "Permanent", "Transparent", "Fragmented"), 
                    "Permanent")
                );

            if (repository.count() < questions.size()) {
                repository.saveAll(questions);
            }
        };
    }
}
