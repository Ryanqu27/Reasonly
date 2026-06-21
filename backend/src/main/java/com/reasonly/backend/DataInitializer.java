package com.reasonly.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.Question.QuestionRepository;
import com.reasonly.backend.Question.QuestionTopic;
import com.reasonly.backend.Question.QuestionType;
import com.reasonly.backend.User.UserSettings.UserLanguage;

@Configuration
public class DataInitializer {

        /*
// Method for testing purposes
    @Bean
    CommandLineRunner init(QuestionRepository repository) {
        return args -> {
            if (repository.count() > 0)
                return;
            List<Question> questions = new ArrayList<>();
            questions.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.SELECT_ALL,
                QuestionDifficulty.BASIC,
                "Which data structures provides the ability for random access?",
                List.of("Array", "Linked List", "Hash Table", "Binary Tree", "Dynamic Array"), List.of("Array", "Dynamic Array")));
            questions.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
                QuestionDifficulty.EASY,
                "Which traversal of a binary search tree outputs sorted values?",
                List.of("Preorder", "Postorder", "Level-order", "Inorder"), List.of("Inorder")));
            questions.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FIND_THE_BUG,
                QuestionDifficulty.EASY, UserLanguage.JAVA,
                "Identify the line number containing the bug in the following Java code:\n\n```java\n1: public class Test {\n2:     public static void main(String[] args) {\n3:         int x = 5;\n4:         if (x = 5) { System.out.println(x); }\n5:     }\n6: }\n```",
                List.of(),
                List.of("4")));
            questions.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
                QuestionDifficulty.EASY,
                "Fill in the blank to complete the logic that iterates from 1 to 5 (inclusive):\n\n```text\niteration_variable = 1\nWHILE iteration_variable <= ___\n    print(iteration_variable)\n    iteration_variable = iteration_variable + 1\n```",
                List.of(),
                List.of("5")));
            questions.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.ORDER_CODE,
                QuestionDifficulty.MEDIUM,
                "Order the following operations correctly to perform a standard Binary Search:",
                List.of(
                    "IF array[mid] < target THEN left = mid + 1", 
                    "mid = left + (right - left) / 2", 
                    "ELSE right = mid - 1",
                    "IF array[mid] == target THEN return mid"
                ), 
                List.of(
                    "mid = left + (right - left) / 2", 
                    "IF array[mid] == target THEN return mid", 
                    "IF array[mid] < target THEN left = mid + 1", 
                    "ELSE right = mid - 1"
                ) 
            ));
            questions.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.CODE_WRITING,
                QuestionDifficulty.EASY,
                "Write a function named 'add' that takes in two integers and returns their sum.",
                List.of("[2, 3]", "[10, -5]", "[0, 0]", "[-1, -1]", "[100, 200]", "[-10, -20]", "[5, 5]", "[1, 1]", "[0, 1]", "[1, 0]"), // Test Case Inputs (JSON parameters array)
                List.of("5", "5", "0", "-2", "300", "-30", "10", "2", "1", "1"),        // Expected Outputs
                "add", // Method name the Runner should invoke
                List.of("[2, 3]", "[1, -5]"), // Sample Test Cases (JSON parameters array)
                List.of("5", "-4") // Sample Expected Outputs
            ));
            repository.saveAll(questions);
        };
    }

        */
// Method for production
        
    @Bean
    CommandLineRunner init(QuestionRepository repository) {
        return args -> {
            if (repository.count() > 0)
                return;

            List<Question> questions = new ArrayList<>();
            questions.addAll(getDSAQuestions());
            questions.addAll(getSystemsQuestions());
            questions.addAll(getNetworkingQuestions());
            questions.addAll(getDatabaseQuestions());
            questions.addAll(getConcurrencyQuestions());
            questions.addAll(getSoftwareDesignQuestions());
            // questions.addAll(getDebuggingQuestions());
            questions.addAll(getLanguageKnowledgeQuestions());

            repository.saveAll(questions);
        };
    }

    private List<Question> getDSAQuestions() {
        List<Question> q = new ArrayList<>();

        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "Which data structure provides average O(1) lookup time?",
            List.of("Array", "Linked List", "Hash Table", "Binary Tree"), List.of("Hash Table")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Why does merge sort require additional memory?", List.of("It uses recursion",
            "It creates temporary arrays", "It swaps elements",
            "It compares adjacent elements"), List.of("It creates temporary arrays")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which algorithm is most commonly used for finding the shortest path in a weighted graph without negative edges?",
            List.of("Breadth-First Search", "Depth-First Search", "Dijkstra's Algorithm",
            "Kruskal's Algorithm"), List.of("Dijkstra's Algorithm")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is the time complexity of searching for an element in a balanced Binary Search Tree?",
            List.of("O(1)", "O(log n)", "O(n)", "O(n log n)"), List.of("O(log n)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the worst-case time complexity of Quick Sort?",
            List.of("O(n log n)", "O(n^2)", "O(n)", "O(log n)"), List.of("O(n^2)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Which algorithm is used to find the minimum spanning tree of a graph?",
            List.of("Dijkstra's", "Prim's", "Bellman-Ford", "Floyd-Warshall"), List.of("Prim's")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the main property of a Heap?", List.of("Elements are sorted",
            "Every node is larger than its children", "It is a full binary tree",
            "O(1) search time"), List.of("Every node is larger than its children")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY,
            "In a doubly linked list, each node has pointers to how many nodes?",
            List.of("One", "Two", "Three", "It depends"), List.of("Two")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the space complexity of a recursive Depth-First Search on a tree of height h?",
            List.of("O(1)", "O(h)", "O(n)", "O(log n)"), List.of("O(h)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "A Red-Black tree is a type of what?",
            List.of("Min-heap", "Self-balancing BST", "B-Tree", "Graph"), List.of("Self-balancing BST")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "The Kadane's algorithm is used to find what?",
            List.of("Shortest path", "Maximum subarray sum",
            "Minimum spanning tree", "Strongly connected components"), List.of("Maximum subarray sum")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the time complexity of finding the kth smallest element in a BST?",
            List.of("O(n)", "O(k)", "O(h + k) where h is height", "O(log n)"), List.of("O(h + k) where h is height")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of a Trie data structure?",
            List.of("Sorting numbers", "Efficient string prefix matching", "Graph traversal",
            "Balancing trees"), List.of("Efficient string prefix matching")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is topological sorting used for?", List.of("Sorting numbers",
            "Ordering tasks with dependencies in a DAG", "Balancing heaps",
            "Finding shortest paths"), List.of("Ordering tasks with dependencies in a DAG")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which algorithm is best for finding strongly connected components?",
            List.of("Dijkstra's", "Tarjan's or Kosaraju's algorithm", "Prim's", "Kruskal's"), List.of("Tarjan's or Kosaraju's algorithm")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the difference between BFS and DFS in terms of memory usage?",
            List.of("No difference",
            "BFS uses more memory in wide graphs", "DFS always uses more memory",
            "Both use O(1) memory"), List.of("BFS uses more memory in wide graphs")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the time complexity of the Floyd-Warshall algorithm?",
            List.of("O(V + E)", "O(V^2)", "O(V^3)", "O(V * E)"), List.of("O(V^3)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is a B+ tree's main advantage over a B-tree for databases?",
            List.of("Faster insertions",
            "All data is stored in leaves, enabling efficient range queries",
            "Less memory usage", "Simpler implementation"), List.of("All data is stored in leaves, enabling efficient range queries")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is an AVL tree's maximum allowed height difference between subtrees?",
            List.of("0", "1", "2", "log n"), List.of("1")));

        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, null,
            "Fill in the blank: A(n) _____ traversal of a binary search tree outputs its values in sorted order.",
            List.of(), List.of("Inorder", "inorder", "in-order")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.BASIC, null,
            "Fill in the blank: A queue follows _____ (First In, First Out) ordering.",
            List.of(), List.of("FIFO", "fifo")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, null,
            "Fill in the blank: The time complexity of inserting an element at the beginning of an array is O(_____), because all existing elements must be shifted.",
            List.of(), List.of("n", "O(n)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: Dynamic Programming avoids redundant computation by storing the results of sub-problems, a technique called _____.",
            List.of(), List.of("memoization", "Memoization")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.HARD, null,
            "Fill in the blank: The time complexity of building a heap from an unordered array of n elements is O(_____), despite the naive expectation of O(n log n).",
            List.of(), List.of("n", "O(n)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: In a circular linked list, the last node points back to the _____ node.",
            List.of(), List.of("first", "head")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EXTREME, null,
            "Fill in the blank: The amortized time complexity of the insert and decrease-key operations in a Fibonacci Heap is O(_____), making it optimal for Dijkstra's algorithm.",
            List.of(), List.of("1", "O(1)")));

        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.SELECT_ALL,
            QuestionDifficulty.BASIC, null,
            "Select ALL data structures that provide O(1) average-case access by index or key:",
            List.of("Array", "Linked List", "Hash Table", "Binary Search Tree", "Stack (via array)"),
            List.of("Array", "Hash Table", "Stack (via array)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.SELECT_ALL,
            QuestionDifficulty.EASY, null,
            "Select ALL sorting algorithms that are considered stable (preserve relative order of equal elements):",
            List.of("Merge Sort", "Quick Sort", "Insertion Sort", "Heap Sort", "Bubble Sort"),
            List.of("Merge Sort", "Insertion Sort", "Bubble Sort")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.SELECT_ALL,
            QuestionDifficulty.MEDIUM, null,
            "Select ALL algorithms that use a greedy strategy:",
            List.of("Dijkstra's Algorithm", "Kruskal's Algorithm", "Prim's Algorithm", "Floyd-Warshall", "Bellman-Ford"),
            List.of("Dijkstra's Algorithm", "Kruskal's Algorithm", "Prim's Algorithm")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.SELECT_ALL,
            QuestionDifficulty.HARD, null,
            "Select ALL properties that are true of a valid Binary Search Tree (BST):",
            List.of(
                "Every left child is less than its parent",
                "Every right child is greater than its parent",
                "The tree must be balanced",
                "Duplicate keys are not allowed by default",
                "In-order traversal yields sorted output"
            ),
            List.of(
                "Every left child is less than its parent",
                "Every right child is greater than its parent",
                "Duplicate keys are not allowed by default",
                "In-order traversal yields sorted output"
            )));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.SELECT_ALL,
            QuestionDifficulty.EXTREME, null,
            "Select ALL true statements about a Bloom Filter:",
            List.of(
                "Can produce false positives",
                "Can produce false negatives",
                "Uses multiple hash functions",
                "Supports efficient deletion by default",
                "Is space-efficient compared to storing all elements"
            ),
            List.of(
                "Can produce false positives",
                "Uses multiple hash functions",
                "Is space-efficient compared to storing all elements"
            )));

        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.ORDER_CODE,
            QuestionDifficulty.MEDIUM, null,
            "Order the steps of the Merge Sort algorithm:",
            List.of(
                "Recursively sort the left half",
                "Merge the two sorted halves back together",
                "Find the middle index to divide the array into two halves",
                "Recursively sort the right half",
                "If the array has 1 or fewer elements, return it"
            ), 
            List.of(
                "If the array has 1 or fewer elements, return it",
                "Find the middle index to divide the array into two halves",
                "Recursively sort the left half",
                "Recursively sort the right half",
                "Merge the two sorted halves back together"
            )));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.ORDER_CODE,
            QuestionDifficulty.BASIC, null,
            "Order the steps to perform a standard Binary Search on a sorted array:",
            List.of(
                "If array[mid] < target, set left = mid + 1",
                "Calculate mid = left + (right - left) / 2",
                "If array[mid] == target, return mid",
                "If array[mid] > target, set right = mid - 1",
                "While left <= right"
            ),
            List.of(
                "While left <= right",
                "Calculate mid = left + (right - left) / 2",
                "If array[mid] == target, return mid",
                "If array[mid] < target, set left = mid + 1",
                "If array[mid] > target, set right = mid - 1"
            )));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.ORDER_CODE,
            QuestionDifficulty.HARD, null,
            "Order the steps of Dijkstra's shortest-path algorithm:",
            List.of(
                "For the current node, update distances to all unvisited neighbors",
                "Mark the initial node with distance 0; all others with infinity",
                "Mark the current node as visited",
                "Select the unvisited node with the smallest known distance as current",
                "Repeat until all nodes are visited or the target is reached"
            ),
            List.of(
                "Mark the initial node with distance 0; all others with infinity",
                "Select the unvisited node with the smallest known distance as current",
                "For the current node, update distances to all unvisited neighbors",
                "Mark the current node as visited",
                "Repeat until all nodes are visited or the target is reached"
            )));

        return q;
    }

    private List<Question> getSystemsQuestions() {
        List<Question> q = new ArrayList<>();

        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.ORDER_CODE, QuestionDifficulty.MEDIUM, null,
            "Order the steps a CPU takes to handle a typical hardware interrupt:",
            List.of(
                "Execute the Interrupt Service Routine (ISR)",
                "Device sends an interrupt signal to the CPU",
                "Save the current process state (registers, program counter)",
                "Restore the saved process state and resume normal execution"
            ),
            List.of(
                "Device sends an interrupt signal to the CPU",
                "Save the current process state (registers, program counter)",
                "Execute the Interrupt Service Routine (ISR)",
                "Restore the saved process state and resume normal execution"
            )));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.ORDER_CODE, QuestionDifficulty.HARD, null,
            "Order the steps that occur when a program accesses a virtual memory page that is not in RAM (a page fault):",
            List.of(
                "OS locates the page on disk",
                "CPU triggers a page fault exception",
                "OS loads the page into a free physical memory frame",
                "OS updates the page table entry",
                "CPU retries the faulting instruction"
            ),
            List.of(
                "CPU triggers a page fault exception",
                "OS locates the page on disk",
                "OS loads the page into a free physical memory frame",
                "OS updates the page table entry",
                "CPU retries the faulting instruction"
            )));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.ORDER_CODE, QuestionDifficulty.BASIC, null,
            "Order the stages of a process lifecycle from creation to termination:",
            List.of(
                "Running",
                "Terminated",
                "Ready",
                "New",
                "Waiting"
            ),
            List.of(
                "New",
                "Ready",
                "Running",
                "Waiting",
                "Terminated"
            )));

        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.BASIC, null,
            "Fill in the blank: The core part of an operating system that manages resources such as CPU, memory, and I/O devices is called the _____.",
            List.of(), List.of("kernel", "Kernel")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EASY, null,
            "Fill in the blank: A thread is often called a _____ process because it is more lightweight than a full process.",
            List.of(), List.of("lightweight", "light-weight")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: The CPU scheduling algorithm that gives the lowest average waiting time is called _____ Job First.",
            List.of(), List.of("Shortest", "shortest")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.HARD, null,
            "Fill in the blank: In Unix, the system call used to create a new child process is called _____().",
            List.of(), List.of("fork", "fork()")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: A _____ bit in memory management indicates that a page frame has been modified since it was loaded into RAM.",
            List.of(), List.of("dirty", "Dirty")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EXTREME, null,
            "Fill in the blank: The hardware component responsible for translating virtual addresses to physical addresses is called the _____ (abbreviation).",
            List.of(), List.of("MMU", "mmu")));

        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.SELECT_ALL, QuestionDifficulty.MEDIUM, null,
            "Select ALL four conditions that are required for a deadlock to occur (Coffman conditions):",
            List.of("Mutual Exclusion", "Hold and Wait", "No Preemption", "Circular Wait", "Starvation", "Priority Inversion"),
            List.of("Mutual Exclusion", "Hold and Wait", "No Preemption", "Circular Wait")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.SELECT_ALL, QuestionDifficulty.BASIC, null,
            "Select ALL that are true about threads versus processes:",
            List.of(
                "Threads share the same memory space within a process",
                "Processes share the same memory space",
                "Threads have their own stack",
                "Context switching between threads is generally faster than between processes",
                "Each process has its own virtual address space"
            ),
            List.of(
                "Threads share the same memory space within a process",
                "Threads have their own stack",
                "Context switching between threads is generally faster than between processes",
                "Each process has its own virtual address space"
            )));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.SELECT_ALL, QuestionDifficulty.HARD, null,
            "Select ALL statements that are true about virtual memory:",
            List.of(
                "It allows a process to use more memory than physically available",
                "It provides memory isolation between processes",
                "Page tables map virtual addresses to physical addresses",
                "All pages are always stored in RAM",
                "TLB caches recent address translations"
            ),
            List.of(
                "It allows a process to use more memory than physically available",
                "It provides memory isolation between processes",
                "Page tables map virtual addresses to physical addresses",
                "TLB caches recent address translations"
            )));

        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What happens during a context switch?",
            List.of("CPU executes a new instruction", "Memory is cleared",
            "CPU state is saved and restored", "A process terminates"), List.of("CPU state is saved and restored")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "Why are system calls generally slower than regular function calls?",
            List.of("They require disk access", "They switch between user and kernel mode",
            "They flush CPU cache",
            "They allocate memory"), List.of("They switch between user and kernel mode")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is the main difference between interrupts and polling?",
            List.of("Polling is always faster", "Interrupts are hardware only",
            "Interrupts let the CPU work on other tasks until notified; polling requires constant checking",
            "Polling is used for high-priority tasks"), List.of("Interrupts let the CPU work on other tasks until notified; polling requires constant checking")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What does an i-node store in a Unix file system?",
            List.of("The file's name", "The actual file content", "Metadata about a file",
            "The user's password"), List.of("Metadata about a file")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is 'thrashing'?",
            List.of("High CPU usage",
            "Excessive paging leading to the OS spending more time swapping than executing",
            "A hardware failure", "Deleting files rapidly"), List.of("Excessive paging leading to the OS spending more time swapping than executing")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.BASIC,
            "What does BIOS stand for?",
            List.of("Binary Input Output System", "Basic Input Output System",
            "Better Input Output System",
            "Basic Internal OS"), List.of("Basic Input Output System")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is the role of a garbage collector?",
            List.of("Deleting unused files", "Reclaiming memory no longer used by the program",
            "Optimizing CPU cycles", "Scanning for viruses"), List.of("Reclaiming memory no longer used by the program")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is the 'working set' of a process?",
            List.of("All memory it can access", "The set of pages it has actively used recently",
            "Its total CPU time", "Its open file descriptors"), List.of("The set of pages it has actively used recently")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is RAID 0 primarily used for?",
            List.of("Data redundancy", "Mirroring", "Performance (striping)", "Error correction"), List.of("Performance (striping)")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.EASY,
            "Which file system is standard for Windows?", List.of("EXT4", "FAT32", "NTFS", "HFS+"), List.of("NTFS")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is a 'Spinlock'?",
            List.of("A lock that puts processes to sleep",
            "A lock where a thread actively waits (loops) until the lock is available",
            "A type of deadlock", "A hardware switch"), List.of("A lock where a thread actively waits (loops) until the lock is available")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the difference between memory-mapped I/O and port-mapped I/O?",
            List.of("No difference", "Memory-mapped uses the same address space as RAM",
            "Port-mapped is faster",
            "Memory-mapped only works on Unix"), List.of("Memory-mapped uses the same address space as RAM")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is a TLB (Translation Lookaside Buffer)?", List.of("A type of RAM",
            "A cache for virtual-to-physical address translations", "A disk buffer",
            "A network buffer"), List.of("A cache for virtual-to-physical address translations")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is copy-on-write (COW)?",
            List.of("A backup strategy",
            "A resource-management technique where copies are made only when modifications occur",
            "A file system type", "A network protocol"), List.of("A resource-management technique where copies are made only when modifications occur")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is a memory barrier/fence?",
            List.of("Physical memory protection",
            "An instruction that prevents memory reordering across it",
            "A firewall for RAM", "A type of cache"), List.of("An instruction that prevents memory reordering across it")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is swapping in operating systems?",
            List.of("Exchanging CPUs", "Moving processes between main memory and disk",
            "Changing file permissions",
            "Switching network adapters"), List.of("Moving processes between main memory and disk")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the difference between hard and soft real-time systems?",
            List.of("Hard systems are more expensive",
            "Hard systems have strict deadlines; missing them causes failure. Soft systems have flexible deadlines",
            "Soft systems are faster", "No difference"), List.of("Hard systems have strict deadlines; missing them causes failure. Soft systems have flexible deadlines")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is a zombie process?",
            List.of("A malicious process",
            "A terminated process whose exit status hasn't been read by its parent",
            "A process with high CPU usage", "A process waiting for I/O"), List.of("A terminated process whose exit status hasn't been read by its parent")));

        return q;
    }

    private List<Question> getNetworkingQuestions() {
        List<Question> q = new ArrayList<>();

        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.ORDER_CODE, QuestionDifficulty.BASIC, null,
            "Order the steps of the TCP 3-way handshake:",
            List.of("Server sends SYN-ACK", "Client sends ACK", "Client sends SYN"), 
            List.of("Client sends SYN", "Server sends SYN-ACK", "Client sends ACK")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.ORDER_CODE, QuestionDifficulty.MEDIUM, null,
            "Order the steps that occur when a browser resolves a domain name (DNS lookup flow):",
            List.of(
                "Browser checks its local cache",
                "OS queries the configured DNS resolver (e.g., ISP resolver)",
                "Resolver queries the root nameserver",
                "Resolver queries the TLD nameserver",
                "Resolver queries the authoritative nameserver and returns the IP"
            ),
            List.of(
                "Browser checks its local cache",
                "OS queries the configured DNS resolver (e.g., ISP resolver)",
                "Resolver queries the root nameserver",
                "Resolver queries the TLD nameserver",
                "Resolver queries the authoritative nameserver and returns the IP"
            )));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.ORDER_CODE, QuestionDifficulty.HARD, null,
            "Order the OSI model layers from Layer 1 (bottom) to Layer 7 (top):",
            List.of(
                "Network",
                "Application",
                "Physical",
                "Transport",
                "Data Link",
                "Session",
                "Presentation"
            ),
            List.of(
                "Physical",
                "Data Link",
                "Network",
                "Transport",
                "Session",
                "Presentation",
                "Application"
            )));

        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EASY, null,
            "Fill in the blank: The protocol that translates human-readable domain names into IP addresses is called _____.",
            List.of(), List.of("DNS", "dns")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.BASIC, null,
            "Fill in the blank: The default port number for HTTPS is _____.",
            List.of(), List.of("443")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EASY, null,
            "Fill in the blank: A _____ address is a unique physical hardware identifier assigned to a network interface card.",
            List.of(), List.of("MAC", "mac")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: IPv6 uses ___-bit addresses, compared to 32-bit for IPv4.",
            List.of(), List.of("128")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.HARD, null,
            "Fill in the blank: The protocol used for routing between different autonomous systems on the internet is called _____ (abbreviation).",
            List.of(), List.of("BGP", "bgp")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EXTREME, null,
            "Fill in the blank: QUIC is a UDP-based transport protocol that serves as the foundation for _____.",
            List.of(), List.of("HTTP/3", "http/3")));

        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.SELECT_ALL, QuestionDifficulty.BASIC, null,
            "Select ALL statements that are true about TCP compared to UDP:",
            List.of(
                "TCP guarantees delivery",
                "TCP is connection-oriented",
                "UDP is faster for low-latency use cases",
                "TCP performs a handshake before sending data",
                "UDP guarantees packet ordering"
            ),
            List.of(
                "TCP guarantees delivery",
                "TCP is connection-oriented",
                "UDP is faster for low-latency use cases",
                "TCP performs a handshake before sending data"
            )));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.SELECT_ALL, QuestionDifficulty.MEDIUM, null,
            "Select ALL that a CDN (Content Delivery Network) is typically used for:",
            List.of(
                "Reducing latency by serving content from servers closer to the user",
                "Storing source code repositories",
                "Offloading traffic from origin servers",
                "Caching static assets like images and scripts",
                "Routing inter-datacenter traffic"
            ),
            List.of(
                "Reducing latency by serving content from servers closer to the user",
                "Offloading traffic from origin servers",
                "Caching static assets like images and scripts"
            )));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.SELECT_ALL, QuestionDifficulty.HARD, null,
            "Select ALL true statements about the TLS handshake:",
            List.of(
                "The client initiates the handshake with a 'Client Hello' message",
                "The server sends its certificate for authentication",
                "A symmetric key is used for the entire handshake",
                "A session key is negotiated during the handshake",
                "TLS can only be used over TCP"
            ),
            List.of(
                "The client initiates the handshake with a 'Client Hello' message",
                "The server sends its certificate for authentication",
                "A session key is negotiated during the handshake"
            )));

        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Why does HTTP/2 improve performance over HTTP/1.1?",
            List.of("Larger packets", "Binary encoding and multiplexing", "More DNS lookups",
            "No headers"), List.of("Binary encoding and multiplexing")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What problem does congestion control solve?", List.of("Packet loss due to encryption",
            "Network overload", "Slow DNS resolution", "IP address exhaustion"), List.of("Network overload")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is a 'socket'?",
            List.of("A physical port", "An endpoint for communication (IP + Port)",
            "A type of cable",
            "A routing table entry"), List.of("An endpoint for communication (IP + Port)")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What does TTL (Time to Live) in an IP packet signify?",
            List.of("Expiration time in seconds",
            "The number of hops the packet can take before being discarded",
            "Packet size", "Encryption level"), List.of("The number of hops the packet can take before being discarded")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is 'latency' in a network?",
            List.of("Data transfer rate",
            "The time delay for a packet to travel from source to destination",
            "Packet loss frequency", "Encryption speed"), List.of("The time delay for a packet to travel from source to destination")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of a Subnet Mask?",
            List.of("Hiding the IP address",
            "Defining the network and host portions of an IP address",
            "Encrypting data", "Allowing remote access"), List.of("Defining the network and host portions of an IP address")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is NAT (Network Address Translation)?",
            List.of("A security protocol",
            "A method of mapping private IP addresses to public IP addresses",
            "A routing algorithm", "A type of firewall"), List.of("A method of mapping private IP addresses to public IP addresses")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the difference between a hub and a switch?",
            List.of("No difference",
            "A switch sends data only to the intended recipient; a hub broadcasts to all",
            "A hub is faster", "A switch uses wireless"), List.of("A switch sends data only to the intended recipient; a hub broadcasts to all")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is ARP (Address Resolution Protocol) used for?", List.of("Resolving domain names",
            "Mapping IP addresses to MAC addresses", "Routing packets",
            "Encrypting data"), List.of("Mapping IP addresses to MAC addresses")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is a VPN?",
            List.of("A type of virus", "A secure tunnel for transmitting data over public networks",
            "A network adapter", "A wireless protocol"), List.of("A secure tunnel for transmitting data over public networks")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the difference between flow control and congestion control?",
            List.of("No difference",
            "Flow control manages sender speed for receiver; congestion control manages network capacity",
            "Congestion control is for local networks", "Flow control uses UDP"), List.of("Flow control manages sender speed for receiver; congestion control manages network capacity")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD, "What is a reverse proxy?",
            List.of("A proxy that blocks websites",
            "A server that sits in front of web servers and forwards client requests",
            "A VPN alternative",
            "A firewall type"), List.of("A server that sits in front of web servers and forwards client requests")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is 'bandwidth'?",
            List.of("Delay of data", "The maximum rate of data transfer across a given path",
            "The distance of a network", "The number of devices connected"), List.of("The maximum rate of data transfer across a given path")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME, "What is TCP slow start?",
            List.of("A connection delay",
            "A congestion control mechanism that gradually increases transmission rate",
            "A security feature", "A timeout mechanism"), List.of("A congestion control mechanism that gradually increases transmission rate")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is an 'Anycast' address?",
            List.of("Sent to all nodes", "Sent to a single specific node",
            "Sent to the closest node in a group",
            "Sent to a random node"), List.of("Sent to the closest node in a group")));

        return q;
    }

    private List<Question> getDatabaseQuestions() {
        List<Question> q = new ArrayList<>();

        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.SELECT_ALL, QuestionDifficulty.MEDIUM, null,
            "Select ALL the properties that refer to ACID in database transactions:",
            List.of("Atomicity", "Asynchronous", "Consistency", "Concurrency", "Isolation", "Immutability", "Durability"), 
            List.of("Atomicity", "Consistency", "Isolation", "Durability")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.SELECT_ALL, QuestionDifficulty.BASIC, null,
            "Select ALL true statements about a Primary Key:",
            List.of(
                "It must be unique within the table",
                "It can contain NULL values",
                "A table can have multiple primary keys",
                "It uniquely identifies each row",
                "It can be composite (multiple columns)"
            ),
            List.of(
                "It must be unique within the table",
                "It uniquely identifies each row",
                "It can be composite (multiple columns)"
            )));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.SELECT_ALL, QuestionDifficulty.HARD, null,
            "Select ALL isolation levels defined by the SQL standard (from least to most strict):",
            List.of("Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable", "Snapshot", "Optimistic"),
            List.of("Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.SELECT_ALL, QuestionDifficulty.EXTREME, null,
            "Select ALL true statements about MVCC (Multi-Version Concurrency Control):",
            List.of(
                "It keeps multiple versions of rows to allow concurrent access",
                "Readers never block writers",
                "Writers never block readers",
                "It eliminates all concurrency anomalies",
                "It is used in PostgreSQL and MySQL InnoDB"
            ),
            List.of(
                "It keeps multiple versions of rows to allow concurrent access",
                "Readers never block writers",
                "Writers never block readers",
                "It is used in PostgreSQL and MySQL InnoDB"
            )));

        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.BASIC, null,
            "Fill in the blank: SQL stands for _____ Query Language.",
            List.of(), List.of("Structured", "structured")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EASY, null,
            "Fill in the blank: The SQL command used to add new rows into a table is _____.",
            List.of(), List.of("INSERT", "insert", "INSERT INTO", "insert into")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: The CAP theorem states that a distributed system can only guarantee two of the following three properties at the same time: Consistency, _____, and Partition Tolerance.",
            List.of(), List.of("Availability", "availability")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.HARD, null,
            "Fill in the blank: The technique of splitting a large database horizontally across multiple servers is called _____.",
            List.of(), List.of("sharding", "Sharding")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: The SQL clause used to filter groups produced by GROUP BY is _____.",
            List.of(), List.of("HAVING", "having")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.FILL_IN_THE_BLANK, QuestionDifficulty.EXTREME, null,
            "Fill in the blank: A _____ index in a database stores the actual data rows at its leaf nodes, sorted by the index key, which means a table can have only one of them.",
            List.of(), List.of("clustered", "Clustered")));

        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.ORDER_CODE, QuestionDifficulty.MEDIUM, null,
            "Order the SQL query clauses in the correct execution order (how the database processes them):",
            List.of(
                "SELECT",
                "WHERE",
                "FROM",
                "GROUP BY",
                "HAVING",
                "ORDER BY"
            ),
            List.of(
                "FROM",
                "WHERE",
                "GROUP BY",
                "HAVING",
                "SELECT",
                "ORDER BY"
            )));

        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "When can an index negatively impact performance?", List.of("During SELECT queries",
            "During INSERT or UPDATE operations", "When reading data",
            "During joins"), List.of("During INSERT or UPDATE operations")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What isolation level prevents dirty reads but allows non-repeatable reads?",
            List.of("Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable"), List.of("Read Committed")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a common reason to choose a NoSQL database over a relational one?",
            List.of("Strong consistency is required",
            "Need for flexible schema or horizontal scalability",
            "Complex joins are frequent", "SQL is too modern"), List.of("Need for flexible schema or horizontal scalability")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is a Foreign Key?",
            List.of("A key from another planet",
            "A field in one table that uniquely identifies a row of another table",
            "A primary key in the current table", "An encrypted column"), List.of("A field in one table that uniquely identifies a row of another table")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is Database Normalization?",
            List.of("Deleting old data",
            "Organizing data to reduce redundancy and improve integrity",
            "Increasing database speed via hardware", "Writing flat files"), List.of("Organizing data to reduce redundancy and improve integrity")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is a 'deadlock' in a database?",
            List.of("A forgotten password", "Two transactions waiting for locks held by each other",
            "A corrupted index", "An empty database"), List.of("Two transactions waiting for locks held by each other")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of the GROUP BY clause?", List.of("Sort the results",
            "Arrange identical data into groups", "Filter individual rows",
            "Combine tables"), List.of("Arrange identical data into groups")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is a 'covering index'?",
            List.of("An index that covers the entire disk",
            "An index that includes all columns needed for a query",
            "A primary key", "A backup index"), List.of("An index that includes all columns needed for a query")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is a View in a database?",
            List.of("A picture of the schema",
            "A virtual table based on the result-set of an SQL statement",
            "A user interface", "A type of table"), List.of("A virtual table based on the result-set of an SQL statement")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What does 'atomicity' in ACID ensure?",
            List.of("Data is stored as atoms",
            "A transaction is treated as a single unit, which either succeeds completely or fails completely",
            "Data is constant", "Transactions are isolated"), List.of("A transaction is treated as a single unit, which either succeeds completely or fails completely")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the difference between INNER JOIN and LEFT JOIN?",
            List.of("No difference",
            "INNER JOIN returns matching rows; LEFT JOIN returns all rows from the left table and matching from the right",
            "INNER JOIN is faster", "LEFT JOIN is for NoSQL"), List.of("INNER JOIN returns matching rows; LEFT JOIN returns all rows from the left table and matching from the right")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is 'Write Ahead Logging' (WAL)?",
            List.of("Writing logs after a crash",
            "Ensuring data changes are recorded in a log before being applied to the database",
            "Logging web requests", "Writing to disk directly"), List.of("Ensuring data changes are recorded in a log before being applied to the database")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is Vertical Scaling in databases?",
            List.of("Adding more servers", "Adding more power (CPU, RAM) to an existing server",
            "Adding more tables", "Adding more rows"), List.of("Adding more power (CPU, RAM) to an existing server")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which NoSQL database type is best for social network relationships?",
            List.of("Document store", "Key-value store", "Graph database", "Column-family store"), List.of("Graph database")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is the 'phantom read' problem?",
            List.of("Reading deleted data",
            "A transaction sees new rows added by another transaction that weren't there before",
            "Reading uncommitted data", "An error during read"), List.of("A transaction sees new rows added by another transaction that weren't there before")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is database partitioning?",
            List.of("Deleting old records",
            "Dividing a database into distinct independent parts to improve performance",
            "Encrypting data",
            "Creating backups"), List.of("Dividing a database into distinct independent parts to improve performance")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a trigger in a database?",
            List.of("A button in the UI",
            "A stored procedure that automatically executes in response to certain events",
            "A type of index", "A backup mechanism"), List.of("A stored procedure that automatically executes in response to certain events")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is replication lag?",
            List.of("Slow queries",
            "The delay between a write on the primary and when it appears on replicas",
            "Network latency", "Index rebuild time"), List.of("The delay between a write on the primary and when it appears on replicas")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a materialized view?",
            List.of("A virtual table",
            "A view whose results are stored physically and refreshed periodically",
            "A 3D database", "A temporary table"), List.of("A view whose results are stored physically and refreshed periodically")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is connection pooling?",
            List.of("Sharing internet connections",
            "Reusing database connections to reduce overhead of creating new connections",
            "A backup strategy", "Load balancing"), List.of("Reusing database connections to reduce overhead of creating new connections")));

        return q;
    }

    private List<Question> getConcurrencyQuestions() {
        List<Question> q = new ArrayList<>();

        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.BASIC, null,
            "Fill in the blank: When multiple threads access shared data simultaneously and the outcome depends on the timing of execution, this is called a _____ condition.",
            List.of(), List.of("race", "Race")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: A variable or abstract data type used to control access to a shared resource by multiple threads is called a _____.",
            List.of(), List.of("semaphore", "Semaphore")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.HARD, null,
            "Fill in the blank: A state where threads constantly change state in response to each other without making actual progress is called _____.",
            List.of(), List.of("livelock", "Livelock")));

        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.SELECT_ALL,
            QuestionDifficulty.HARD, null,
            "Select ALL true statements about lock-free programming:",
            List.of(
                "It uses Compare-And-Swap (CAS) operations",
                "It guarantees that at least one thread always makes progress",
                "It is always faster than using locks",
                "It can suffer from the ABA problem",
                "It requires no synchronization whatsoever"
            ),
            List.of(
                "It uses Compare-And-Swap (CAS) operations",
                "It guarantees that at least one thread always makes progress",
                "It can suffer from the ABA problem"
            )));
        
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.ORDER_CODE,
            QuestionDifficulty.MEDIUM, null,
            "Order the steps a thread goes through when using a standard mutex lock to access a shared resource:",
            List.of(
                "Access the shared resource",
                "Release the lock",
                "Attempt to acquire the lock",
                "Wait (block) if the lock is held by another thread"
            ),
            List.of(
                "Attempt to acquire the lock",
                "Wait (block) if the lock is held by another thread",
                "Access the shared resource",
                "Release the lock"
            )));

        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which condition is required for a deadlock?",
            List.of("Preemption", "Mutual exclusion", "Parallelism", "Caching"), List.of("Mutual exclusion")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Why can fine-grained locking improve performance?",
            List.of("It increases lock contention", "It reduces context switches",
            "It limits the scope of locking",
            "It prevents race conditions entirely"), List.of("It limits the scope of locking")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "How does optimistic locking differ from pessimistic locking?",
            List.of("Pessimistic locking is faster",
            "Optimistic locking assumes conflicts are rare and checks at commit; pessimistic locking locks data upfront",
            "Optimistic locking never fails",
            "Pessimistic locking is only for read-only data"), List.of("Optimistic locking assumes conflicts are rare and checks at commit; pessimistic locking locks data upfront")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'Thread Pool'?",
            List.of("A collection of dormant threads",
            "A managed set of reusable threads for executing tasks",
            "A type of memory segment", "A debugging tool"), List.of("A managed set of reusable threads for executing tasks")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the 'Double-Checked Locking' pattern used for?",
            List.of("Reducing concurrency",
            "Lazy initialization in a thread-safe manner with minimal overhead",
            "Detecting deadlocks", "Hashing data"), List.of("Lazy initialization in a thread-safe manner with minimal overhead")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'wait-free' algorithm?",
            List.of("An algorithm that never waits for user input",
            "An algorithm that guarantees every thread makes progress in a finite number of steps",
            "A single-threaded algorithm", "An algorithm with no loops"), List.of("An algorithm that guarantees every thread makes progress in a finite number of steps")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the 'ABA problem' in lock-free programming?",
            List.of("A memory leak",
            "A thread sees a value is 'A', then 'B', then 'A' again, and mistakenly assumes nothing changed",
            "A type of deadlock", "A circular dependency"), List.of("A thread sees a value is 'A', then 'B', then 'A' again, and mistakenly assumes nothing changed")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'Reentrant Lock'?",
            List.of("A lock that can't be released",
            "A lock that allows the thread currently holding it to acquire it again without deadlocking",
            "A lock for files", "A hardware-level lock"), List.of("A lock that allows the thread currently holding it to acquire it again without deadlocking")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is 'Priority Inversion'?",
            List.of("High priority threads running first",
            "A low-priority task holds a resource needed by a high-priority task",
            "A thread losing its priority", "Sorting threads by priority"), List.of("A low-priority task holds a resource needed by a high-priority task")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is 'False Sharing'?",
            List.of("Sharing wrong data",
            "Multiple processors updating variables that reside on the same cache line",
            "A network error",
            "Using a global variable unnecessarily"), List.of("Multiple processors updating variables that reside on the same cache line")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is a 'Read-Write Lock'?",
            List.of("A lock that allows only one reader",
            "A lock that allows multiple concurrent readers but exclusive access for writers",
            "A lock for a disk drive", "A type of database index"), List.of("A lock that allows multiple concurrent readers but exclusive access for writers")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'Barrier' in concurrency?",
            List.of("A security wall",
            "A synchronization point where multiple threads must wait until all threads reach it",
            "A crashed thread", "A network gateway"), List.of("A synchronization point where multiple threads must wait until all threads reach it")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is 'Starvation' in concurrency?",
            List.of("A thread dying of low memory",
            "A thread is perpetually denied necessary resources to process its work",
            "The system running out of data", "A server shutdown"), List.of("A thread is perpetually denied necessary resources to process its work")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the 'Fork/Join' framework used for?",
            List.of("Connecting databases",
            "Parallel execution of tasks that can be broken into smaller subtasks recursively",
            "Merging Git branches", "Joining strings"), List.of("Parallel execution of tasks that can be broken into smaller subtasks recursively")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is Compare-And-Swap (CAS)?",
            List.of("A sorting algorithm",
            "An atomic instruction that compares and conditionally updates a memory location",
            "A garbage collection technique", "A thread scheduling method"), List.of("An atomic instruction that compares and conditionally updates a memory location")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a monitor in concurrency?",
            List.of("A display screen",
            "A synchronization construct that bundles mutual exclusion and condition variables",
            "A debugging tool", "A thread pool"), List.of("A synchronization construct that bundles mutual exclusion and condition variables")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is a condition variable?",
            List.of("A boolean flag",
            "A synchronization primitive that allows threads to wait until a condition is met",
            "A global constant", "A thread-local variable"), List.of("A synchronization primitive that allows threads to wait until a condition is met")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is thread local storage?",
            List.of("Shared memory", "Memory that is unique to each thread", "Global variables",
            "Cache memory"), List.of("Memory that is unique to each thread")));
        return q;
    }

    private List<Question> getSoftwareDesignQuestions() {
        List<Question> q = new ArrayList<>();

        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.BASIC, null,
            "Fill in the blank: The first letter 'S' in the SOLID principles stands for Single _____ Principle.",
            List.of(), List.of("Responsibility", "responsibility")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, null,
            "Fill in the blank: The software design principle 'DRY' stands for Don't _____ Yourself.",
            List.of(), List.of("Repeat", "repeat")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: The design pattern that ensures a class has only one instance and provides a global point of access to it is called the _____ pattern.",
            List.of(), List.of("Singleton", "singleton")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, null,
            "Fill in the blank: The design principle that states high-level modules should not depend on low-level modules; both should depend on abstractions is called _____ Inversion.",
            List.of(), List.of("Dependency", "dependency")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.HARD, null,
            "Fill in the blank: The architectural pattern CQRS stands for Command _____ Responsibility Segregation.",
            List.of(), List.of("Query", "query")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EXTREME, null,
            "Fill in the blank: _____ Sourcing is a pattern where all changes to application state are stored as an immutable sequence of events, rather than just the current state.",
            List.of(), List.of("Event", "event")));

        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.SELECT_ALL,
            QuestionDifficulty.EASY, null,
            "Select ALL letters that are part of the SOLID principles:",
            List.of("S - Single Responsibility", "O - Open-Closed", "L - Liskov Substitution", "I - Interface Segregation", "D - Dependency Inversion", "A - Abstraction First"),
            List.of("S - Single Responsibility", "O - Open-Closed", "L - Liskov Substitution", "I - Interface Segregation", "D - Dependency Inversion")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.SELECT_ALL,
            QuestionDifficulty.MEDIUM, null,
            "Select ALL GoF (Gang of Four) creational design patterns:",
            List.of("Singleton", "Factory Method", "Abstract Factory", "Observer", "Builder", "Prototype", "Decorator"),
            List.of("Singleton", "Factory Method", "Abstract Factory", "Builder", "Prototype")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.SELECT_ALL,
            QuestionDifficulty.HARD, null,
            "Select ALL true statements about Dependency Injection (DI):",
            List.of(
                "It improves testability by allowing dependencies to be mocked",
                "It increases coupling between components",
                "It is a form of Inversion of Control",
                "It can be achieved via constructor injection",
                "It requires a specific framework to implement"
            ),
            List.of(
                "It improves testability by allowing dependencies to be mocked",
                "It is a form of Inversion of Control",
                "It can be achieved via constructor injection"
            )));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.SELECT_ALL,
            QuestionDifficulty.EXTREME, null,
            "Select ALL true statements about Domain-Driven Design (DDD):",
            List.of(
                "It centers design on the core domain and domain logic",
                "Bounded Contexts define the scope of a domain model",
                "Aggregates are clusters of objects treated as a single unit",
                "DDD is only applicable to microservices",
                "Ubiquitous Language is shared between developers and domain experts"
            ),
            List.of(
                "It centers design on the core domain and domain logic",
                "Bounded Contexts define the scope of a domain model",
                "Aggregates are clusters of objects treated as a single unit",
                "Ubiquitous Language is shared between developers and domain experts"
            )));

        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.ORDER_CODE,
            QuestionDifficulty.MEDIUM, null,
            "Order the GoF design pattern categories and their typical usage order when designing a system (broadest to most specific structural concern):",
            List.of(
                "Structural patterns (how classes/objects are composed)",
                "Creational patterns (how objects are created)",
                "Behavioral patterns (how objects interact and communicate)"
            ),
            List.of(
                "Creational patterns (how objects are created)",
                "Structural patterns (how classes/objects are composed)",
                "Behavioral patterns (how objects interact and communicate)"
            )));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.ORDER_CODE,
            QuestionDifficulty.HARD, null,
            "Order the steps for implementing the Builder pattern to construct a complex object:",
            List.of(
                "Call build() to get the final object",
                "Create a Builder class with setter methods for each field",
                "Call each setter to configure the desired fields",
                "Instantiate the Builder",
                "Make the target class's constructor private"
            ),
            List.of(
                "Make the target class's constructor private",
                "Create a Builder class with setter methods for each field",
                "Instantiate the Builder",
                "Call each setter to configure the desired fields",
                "Call build() to get the final object"
            )));

        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "Which principle encourages programming to an interface?",
            List.of("Encapsulation", "Polymorphism", "Abstraction", "Inheritance"), List.of("Abstraction")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which design pattern decouples object creation from usage?",
            List.of("Singleton", "Factory", "Observer", "Decorator"), List.of("Factory")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Why is dependency injection beneficial?",
            List.of("Improves execution speed", "Reduces memory usage",
            "Improves testability and flexibility", "Simplifies syntax"), List.of("Improves testability and flexibility")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the main benefit of using the Repository pattern?",
            List.of("Improves query speed", "Decouples the business logic from data access details",
            "Replaces the database entirely", "Simplifies HTML templates"), List.of("Decouples the business logic from data access details")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which SOLID principle suggests that classes should be open for extension but closed for modification?",
            List.of("Single Responsibility", "Open-Closed Principle", "Liskov Substitution",
            "Interface Segregation"), List.of("Open-Closed Principle")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is Encapsulation?",
            List.of("Making all variables public",
            "Bundling data and methods that work on that data within a single unit",
            "Creating many objects", "Using inheritance"), List.of("Bundling data and methods that work on that data within a single unit")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the primary purpose of the Observer pattern?",
            List.of("To hide data",
            "To define a one-to-many dependency so when one object changes state, all dependents are notified",
            "To create unique objects", "To speed up algorithms"), List.of("To define a one-to-many dependency so when one object changes state, all dependents are notified")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Which pattern is used to provide a unified interface to a set of interfaces in a subsystem?",
            List.of("Adapter", "Facade", "Bridge", "Composite"), List.of("Facade")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What does 'Composition over Inheritance' mean?",
            List.of("Write more classes",
            "Reuse code by containing instances of other classes rather than inheriting",
            "Always use inheritance", "Don't use classes"), List.of("Reuse code by containing instances of other classes rather than inheriting")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Liskov Substitution Principle?",
            List.of("Subclasses should be able to replace their base classes without affecting correctness",
            "Every class must have an interface", "Don't use subclasses",
            "Use substitution for speed"), List.of("Subclasses should be able to replace their base classes without affecting correctness")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the 'State' pattern used for?",
            List.of("Storing user data",
            "Allowing an object to alter its behavior when its internal state changes",
            "Saving the app's state to disk", "Managing network states"), List.of("Allowing an object to alter its behavior when its internal state changes")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Which pattern is best for attaching additional responsibilities to an object dynamically?",
            List.of("Proxy", "Decorator", "Strategy", "Chain of Responsibility"), List.of("Decorator")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of the 'Strategy' pattern?",
            List.of("Winning a game",
            "Defining a family of algorithms and making them interchangeable",
            "Encapsulating data", "Providing a backup plan"), List.of("Defining a family of algorithms and making them interchangeable")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What does 'Loose Coupling' imply?",
            List.of("Components are highly dependent",
            "Components have little knowledge of each other, making them easier to change",
            "The system is slow", "The code is disorganized"), List.of("Components have little knowledge of each other, making them easier to change")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of the 'Template Method' pattern?",
            List.of("To create many templates",
            "Defining the skeleton of an algorithm and letting subclasses redefine certain steps",
            "To format HTML", "To manage user logins"), List.of("Defining the skeleton of an algorithm and letting subclasses redefine certain steps")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is 'Inversion of Control' (IoC)?",
            List.of("Inverting the user interface",
            "A design principle where the control of objects or portions of a program is transferred to a container or framework",
            "Closing the program early", "Reversing the flow of data"), List.of("A design principle where the control of objects or portions of a program is transferred to a container or framework")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is 'Polymorphism'?",
            List.of("Creating many files", "The ability of an object to take on many forms",
            "Using many processors", "Writing code in multiple languages"), List.of("The ability of an object to take on many forms")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the 'Command' pattern?",
            List.of("Using a terminal",
            "Encapsulating a request as an object, thereby letting you parameterize clients with different requests",
            "Giving orders to the CPU", "A type of script"), List.of("Encapsulating a request as an object, thereby letting you parameterize clients with different requests")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the Hexagonal Architecture (Ports and Adapters)?",
            List.of("A physical server layout",
            "An architectural pattern that isolates the core logic from external concerns using ports and adapters",
            "A database schema", "A network topology"), List.of("An architectural pattern that isolates the core logic from external concerns using ports and adapters")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the Adapter pattern?",
            List.of("A power converter",
            "A pattern that allows incompatible interfaces to work together",
            "A database connector", "A thread wrapper"), List.of("A pattern that allows incompatible interfaces to work together")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Chain of Responsibility pattern?",
            List.of("A management hierarchy",
            "A pattern where a request is passed along a chain of handlers until one handles it",
            "Error handling", "A linked list"), List.of("A pattern where a request is passed along a chain of handlers until one handles it")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is cohesion in software design?",
            List.of("Gluing modules together",
            "The degree to which elements of a module belong together",
            "Code duplication", "Inheritance depth"), List.of("The degree to which elements of a module belong together")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Builder pattern used for?",
            List.of("Building servers",
            "Constructing complex objects step by step, allowing different representations",
            "Compiling code", "Managing dependencies"), List.of("Constructing complex objects step by step, allowing different representations")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Prototype pattern?", List.of("Initial code version",
            "Creating new objects by cloning an existing object", "A testing mock",
            "A first draft design"), List.of("Creating new objects by cloning an existing object")));

        return q;
    }

    private List<Question> getLanguageKnowledgeQuestions() {
        List<Question> q = new ArrayList<>();
        
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY, UserLanguage.JAVA,
            "Which keyword is used to prevent a class from being subclassed in Java?",
            List.of("static", "const", "final", "sealed"), List.of("final")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.JAVA,
            "Fill in the blank: The transient keyword in Java is used to indicate that a field should not be ____.",
            List.of(), List.of("serialized", "Serialized")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, UserLanguage.JAVA,
            "What is the effect of the 'volatile' keyword in Java?",
            List.of("Prevents race conditions", "Ensures visibility of changes to other threads",
            "Locks the variable", "Makes the variable constant"), List.of("Ensures visibility of changes to other threads")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, UserLanguage.JAVA,
            "Fill in the blank: In Java, the keyword used to explicitly call the parent class's constructor from a subclass is _____.",
            List.of(), List.of("super", "super()")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.SELECT_ALL,
            QuestionDifficulty.MEDIUM, UserLanguage.JAVA,
            "Select ALL access modifiers available in Java:",
            List.of("public", "private", "protected", "internal", "package-private", "static"),
            List.of("public", "private", "protected", "package-private")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FIND_THE_BUG,
            QuestionDifficulty.EASY, UserLanguage.JAVA,
            "Identify the line number containing the bug in the following Java code:\n\n```java\n1: public class Test {\n2:     public static void main(String[] args) {\n3:         int x = 5;\n4:         if (x = 5) { System.out.println(x); }\n5:     }\n6: }\n```",
            List.of(), List.of("4")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, UserLanguage.JAVA,
            "Fill in the blank: In Java, the _____ keyword ensures that only one thread can execute a given method or block at a time.",
            List.of(), List.of("synchronized", "Synchronized")));

        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, UserLanguage.PYTHON,
            "What replaces the concept of a 'null' typical in other languages in Python?",
            List.of("undefined", "None", "Null", "NaN"), List.of("None")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, UserLanguage.PYTHON,
            "Fill in the blank: In Python, you define a function using the ____ keyword.",
            List.of(), List.of("def", "DEF")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.PYTHON,
            "Fill in the blank: In Python, a decorator is applied to a function using the ____ symbol followed by the decorator name.",
            List.of(), List.of("@")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.SELECT_ALL,
            QuestionDifficulty.MEDIUM, UserLanguage.PYTHON,
            "Select ALL true statements about Python lists:",
            List.of(
                "Lists are ordered",
                "Lists allow duplicate elements",
                "Lists are immutable",
                "Lists are indexed starting at 0",
                "Lists can only contain one data type"
            ),
            List.of(
                "Lists are ordered",
                "Lists allow duplicate elements",
                "Lists are indexed starting at 0"
            )));

        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, UserLanguage.JAVASCRIPT,
            "Which of the following is NOT a valid way to declare a variable in modern JavaScript?",
            List.of("let", "const", "var", "def"), List.of("def")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FIND_THE_BUG,
            QuestionDifficulty.MEDIUM, UserLanguage.JAVASCRIPT,
            "Identify the line number with the bug in this JavaScript code:\n" +
            "```javascript\n" +
            "1: function greet() {\n" +
            "2:   const message;\n" +
            "3:   message = 'hello';\n" +
            "4:   return message;\n" +
            "5: }\n" +
            "```",
            List.of(), List.of("2")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, UserLanguage.JAVASCRIPT,
            "Fill in the blank: In JavaScript, the ____ keyword declares a block-scoped variable that cannot be reassigned.",
            List.of(), List.of("const")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.SELECT_ALL,
            QuestionDifficulty.HARD, UserLanguage.JAVASCRIPT,
            "Select ALL true statements about JavaScript Promises:",
            List.of(
                "A Promise can be in one of three states: pending, fulfilled, or rejected",
                "Promise.all() rejects if any one promise rejects",
                "Promises are synchronous",
                "async/await is syntactic sugar over Promises",
                "A resolved Promise can later become rejected"
            ),
            List.of(
                "A Promise can be in one of three states: pending, fulfilled, or rejected",
                "Promise.all() rejects if any one promise rejects",
                "async/await is syntactic sugar over Promises"
            )));

        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD, UserLanguage.C_PLUS_PLUS,
            "In C++, what does the 'virtual' keyword inside a base class method declaration do?",
            List.of("Makes the method private", "Allows the method to be overridden in derived classes via late binding", "Prevents the method from being inherited", "Forces the method to be inline"), List.of("Allows the method to be overridden in derived classes via late binding")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.C_PLUS_PLUS,
            "Fill in the blank: The standard input stream object in C++ is called ____.",
            List.of(), List.of("cin", "std::cin")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FIND_THE_BUG,
            QuestionDifficulty.HARD, UserLanguage.C_PLUS_PLUS,
            "Identify the line number containing the bug in this C++ code:\n\n```cpp\n1: #include <iostream>\n2: int main() {\n3:     int* ptr = new int(10);\n4:     std::cout << *ptr << std::endl;\n5:     delete ptr;\n6:     std::cout << *ptr << std::endl;\n7: }\n```",
            List.of(), List.of("6")));

        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, UserLanguage.C_SHARP,
            "In C#, which keyword is used to handle exceptions?",
            List.of("try...catch", "except", "throws", "rescue"), List.of("try...catch")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.HARD, UserLanguage.C_SHARP,
            "Fill in the blank: In C#, Language Integrated Query is commonly abbreviated as ____.",
            List.of(), List.of("LINQ", "linq", "Linq")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.C_SHARP,
            "Fill in the blank: In C#, the keyword used to define an asynchronous method is _____.",
            List.of(), List.of("async")));

        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD, UserLanguage.GO,
            "How do you declare a public function stored in a Go package?",
            List.of("Use the 'public' keyword", "Start the function name with a capital letter", "Use the '@export' decorator", "Functions are always public in Go"), List.of("Start the function name with a capital letter")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.GO,
            "Fill in the blank: In Go, lightweight threads managed by the Go runtime are called ____.",
            List.of(), List.of("goroutines", "Goroutines", "goroutine")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.SELECT_ALL,
            QuestionDifficulty.HARD, UserLanguage.GO,
            "Select ALL true statements about Go's concurrency model:",
            List.of(
                "Goroutines are multiplexed onto OS threads by the Go runtime",
                "Channels are used to communicate between goroutines",
                "Go uses a thread-per-request model like Java",
                "The 'go' keyword starts a new goroutine",
                "Goroutines have the same stack size as OS threads"
            ),
            List.of(
                "Goroutines are multiplexed onto OS threads by the Go runtime",
                "Channels are used to communicate between goroutines",
                "The 'go' keyword starts a new goroutine"
            )));

        return q;
    }
}
