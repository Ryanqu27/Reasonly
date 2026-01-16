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
                new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                    "Which data structure provides average O(1) lookup time?",
                    List.of("Array", "Linked List", "Hash Table", "Binary Tree"),
                    "Hash Table"),

                new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EASY,
                    "Which traversal of a binary search tree outputs sorted values?",
                    List.of("Preorder", "Postorder", "Level-order", "Inorder"),
                    "Inorder"),

                new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                    "What happens to time complexity when a recursive algorithm recomputes overlapping subproblems?",
                    List.of("Becomes linear", "Becomes exponential", "Remains constant", "Becomes logarithmic"),
                    "Becomes exponential"),

                new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                    "Why does merge sort require additional memory?",
                    List.of("It uses recursion", "It creates temporary arrays", "It swaps elements", "It compares adjacent elements"),
                    "It creates temporary arrays"),

                new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC,
                    "What does this code print?\nint x = 1;\nfor(int i = 0; i < 3; i++) x += i;\nSystem.out.println(x);",
                    List.of("3", "4", "5", "6"),
                    "4"),

                new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                    "What is the final value of x?\nint x = 10;\nif(x > 5) x += 2;\nelse x -= 2;",
                    List.of("8", "10", "12", "14"),
                    "12"),

                new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.BASIC,
                    "What is the primary purpose of virtual memory?",
                    List.of("Increase CPU speed", "Allow programs to use more memory than physically available",
                            "Prevent deadlocks", "Store cache data"),
                    "Allow programs to use more memory than physically available"),

                new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                    "What happens during a context switch?",
                    List.of("CPU executes a new instruction", "Memory is cleared",
                            "CPU state is saved and restored", "A process terminates"),
                    "CPU state is saved and restored"),

                new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD,
                    "Why are system calls generally slower than regular function calls?",
                    List.of("They require disk access", "They switch between user and kernel mode",
                            "They flush CPU cache", "They allocate memory"),
                    "They switch between user and kernel mode"),

                new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC,
                    "Which protocol guarantees reliable data delivery?",
                    List.of("UDP", "IP", "TCP", "HTTP"),
                    "TCP"),

                new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                    "Why does HTTP/2 improve performance over HTTP/1.1?",
                    List.of("Larger packets", "Binary encoding and multiplexing",
                            "More DNS lookups", "No headers"),
                    "Binary encoding and multiplexing"),

                new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                    "What problem does congestion control solve?",
                    List.of("Packet loss due to encryption", "Network overload",
                            "Slow DNS resolution", "IP address exhaustion"),
                    "Network overload"),

                new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC,
                    "What does an index primarily improve?",
                    List.of("Insert speed", "Delete speed", "Query lookup speed", "Transaction safety"),
                    "Query lookup speed"),

                new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                    "When can an index negatively impact performance?",
                    List.of("During SELECT queries", "During INSERT or UPDATE operations",
                            "When reading data", "During joins"),
                    "During INSERT or UPDATE operations"),

                new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD,
                    "What isolation level prevents dirty reads but allows non-repeatable reads?",
                    List.of("Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable"),
                    "Read Committed"),

                new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC,
                    "What is a race condition?",
                    List.of("Threads competing for CPU time",
                            "Multiple threads accessing shared data unsafely",
                            "Dead threads",
                            "Infinite loops"),
                    "Multiple threads accessing shared data unsafely"),

                new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                    "Which condition is required for a deadlock?",
                    List.of("Preemption", "Mutual exclusion", "Parallelism", "Caching"),
                    "Mutual exclusion"),

                new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD,
                    "Why can fine-grained locking improve performance?",
                    List.of("It increases lock contention", "It reduces context switches",
                            "It limits the scope of locking", "It prevents race conditions entirely"),
                    "It limits the scope of locking"),

                new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC,
                    "Which principle encourages programming to an interface?",
                    List.of("Encapsulation", "Polymorphism", "Abstraction", "Inheritance"),
                    "Abstraction"),

                new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                    "Which design pattern decouples object creation from usage?",
                    List.of("Singleton", "Factory", "Observer", "Decorator"),
                    "Factory"),

                new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                    "Why is dependency injection beneficial?",
                    List.of("Improves execution speed", "Reduces memory usage",
                            "Improves testability and flexibility", "Simplifies syntax"),
                    "Improves testability and flexibility"),

                new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC,
                    "What is the first step in debugging unexpected behavior?",
                    List.of("Rewrite the code", "Add more features",
                            "Reproduce the issue", "Deploy to production"),
                    "Reproduce the issue"),

                new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM,
                    "Why are logs useful in debugging?",
                    List.of("They improve performance", "They replace tests",
                            "They provide execution context", "They reduce memory usage"),
                    "They provide execution context"),

                new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD,
                    "What makes concurrency bugs difficult to reproduce?",
                    List.of("They depend on timing and thread scheduling",
                            "They only occur in production",
                            "They cause compile errors",
                            "They occur deterministically"),
                    "They depend on timing and thread scheduling")
            );

            if (repository.count() < questions.size()) {
                repository.saveAll(questions);
            }
        };
    }
}
