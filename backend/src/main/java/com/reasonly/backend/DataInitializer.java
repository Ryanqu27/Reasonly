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
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, null,
            "Fill in the blank: A(n) _____ traversal of a binary search tree outputs its values in sorted order.",
            List.of(), List.of("Inorder", "inorder", "in-order")));
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
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Why does merge sort require additional memory?", List.of("It uses recursion",
            "It creates temporary arrays", "It swaps elements",
            "It compares adjacent elements"), List.of("It creates temporary arrays")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is the primary advantage of a Bloom Filter?",
            List.of("O(1) deletion", "Space efficiency for set membership tests",
            "Always returns exact results",
            "Guarantees no false positives"), List.of("Space efficiency for set membership tests")));
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
            "Which data structure is typically used to implement a LIFO (Last In, First Out) behavior?",
            List.of("Queue", "Stack", "Priority Queue", "Linked List"), List.of("Stack")));
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
            QuestionDifficulty.BASIC,
            "Which of these is a stable sorting algorithm?",
            List.of("Quick Sort", "Merge Sort", "Heap Sort", "Selection Sort"), List.of("Merge Sort")));
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
            "Which sorting algorithm has the best average-case performance?",
            List.of("Bubble Sort", "Merge Sort", "Insertion Sort", "Selection Sort"), List.of("Merge Sort")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY,
            "What is the time complexity of inserting an element at the beginning of an array?",
            List.of("O(1)", "O(n)", "O(log n)", "O(1) amortized"), List.of("O(n)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is the primary use of a Queue?",
            List.of("LIFO processing", "FIFO processing", "Sorting data",
            "Storing key-value pairs"), List.of("FIFO processing")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Dynamic Programming is based on which concept?",
            List.of("Greedy selection", "Dividing into smaller subproblems and storing results",
            "Randomized trials", "Systematic searching"), List.of("Dividing into smaller subproblems and storing results")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Which of these is an NP-complete problem?",
            List.of("Shortest path", "Traveling Salesman Problem", "Sorting an array",
            "Binary search"), List.of("Traveling Salesman Problem")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY,
            "What is the height of a balanced tree with n nodes?",
            List.of("O(n)", "O(log n)", "O(sqrt(n))", "O(n^2)"), List.of("O(log n)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "The Kadane's algorithm is used to find what?",
            List.of("Shortest path", "Maximum subarray sum",
            "Minimum spanning tree", "Strongly connected components"), List.of("Maximum subarray sum")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the time complexity of building a heap from an array of n elements?",
            List.of("O(n log n)", "O(n)", "O(n^2)", "O(log n)"), List.of("O(n)")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "Which structure is best for implementing a dictionary?",
            List.of("Stack", "Hash Map", "Linked List", "Array"), List.of("Hash Map")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "A circular linked list has no what?",
            List.of("Nodes", "Pointers", "Null ending", "Values"), List.of("Null ending")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the amortized time complexity of operations in a Fibonacci heap?",
            List.of("O(log n) for all operations",
            "O(1) for insert and decrease-key, O(log n) for extract-min",
            "O(n) for all operations", "O(log log n) for insert"), List.of("O(1) for insert and decrease-key, O(log n) for extract-min")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "Which data structure is used to efficiently solve the Range Minimum Query problem?",
            List.of("Binary Search Tree", "Sparse Table or Segment Tree", "Hash Table", "Trie"), List.of("Sparse Table or Segment Tree")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the time complexity of finding the kth smallest element in a BST?",
            List.of("O(n)", "O(k)", "O(h + k) where h is height", "O(log n)"), List.of("O(h + k) where h is height")));
        q.add(new Question(null, QuestionTopic.DATA_STRUCTURES_AND_ALGORITHMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is a B+ tree's main advantage over a B-tree for databases?",
            List.of("Faster insertions",
            "All data is stored in leaves, enabling efficient range queries",
            "Less memory usage", "Simpler implementation"), List.of("All data is stored in leaves, enabling efficient range queries")));
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
            QuestionDifficulty.EXTREME,
            "What is the time complexity of the Floyd-Warshall algorithm?",
            List.of("O(V + E)", "O(V^2)", "O(V^3)", "O(V * E)"), List.of("O(V^3)")));
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
            "What is an AVL tree's maximum allowed height difference between subtrees?",
            List.of("0", "1", "2", "log n"), List.of("1")));
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
        q.add(new Question(
            null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.BASIC,
            "What is a kernel?",
            List.of("The user interface",
            "The core part of the OS managing resources", "A type of CPU",
            "A file system"), List.of("The core part of the OS managing resources")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is a 'deadlock' in an operating system?",
            List.of("A crashed program",
            "A state where two processes are stuck waiting for each other",
            "A memory leak", "A slow network connection"), List.of("A state where two processes are stuck waiting for each other")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is the role of a garbage collector?",
            List.of("Deleting unused files", "Reclaiming memory no longer used by the program",
            "Optimizing CPU cycles", "Scanning for viruses"), List.of("Reclaiming memory no longer used by the program")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.EASY,
            "Which component manages the execution of processes?",
            List.of("Memory Manager", "Scheduler", "File System", "I/O Manager"), List.of("Scheduler")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is the 'working set' of a process?",
            List.of("All memory it can access", "The set of pages it has actively used recently",
            "Its total CPU time", "Its open file descriptors"), List.of("The set of pages it has actively used recently")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is thrashing?",
            List.of("High CPU usage",
            "Excessive paging lead by the OS spending more time swapping than executing",
            "A hardware failure", "Deleting files rapidly"), List.of("Excessive paging lead by the OS spending more time swapping than executing")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.BASIC,
            "What does BIOS stand for?",
            List.of("Binary Input Output System", "Basic Input Output System",
            "Better Input Output System",
            "Basic Internal OS"), List.of("Basic Input Output System")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is a shell?",
            List.of("The hardware casing", "A command-line interpreter for the OS",
            "A type of virus",
            "A database engine"), List.of("A command-line interpreter for the OS")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "Which system call is used to create a new process in Unix?",
            List.of("new()", "exec()", "fork()", "spawn()"), List.of("fork()")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.EASY,
            "A 'thread' is often called a what?",
            List.of("Heavyweight process", "Lightweight process", "Kernel task", "Sub-process"), List.of("Lightweight process")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is the purpose of an OS page table?", List.of("Store file names",
            "Map virtual addresses to physical addresses", "Track open processes",
            "Manage CPU registers"), List.of("Map virtual addresses to physical addresses")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "A 'Segmentation Fault' typically occurs when?", List.of("The CPU overheats",
            "A program tries to access memory it doesn't own", "The disk is full",
            "The network is down"), List.of("A program tries to access memory it doesn't own")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.BASIC,
            "What is cache memory?",
            List.of("Slow, high-capacity storage", "Fast, small memory near the CPU",
            "A backup system",
            "Virtual memory on disk"), List.of("Fast, small memory near the CPU")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "Which CPU scheduling algorithm gives the lowest average waiting time?",
            List.of("First-Come First-Served", "Shortest Job First", "Round Robin",
            "Priority Scheduling"), List.of("Shortest Job First")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is RAID 0 primarily used for?",
            List.of("Data redundancy", "Mirroring", "Performance (striping)", "Error correction"), List.of("Performance (striping)")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.BASIC,
            "What is an interrupt?",
            List.of("A program crash", "A signal from hardware or software requiring CPU attention",
            "A network pause", "A user input error"), List.of("A signal from hardware or software requiring CPU attention")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is 'dirty' bit in memory management?",
            List.of("Corrupted data",
            "A bit indicating a page has been modified since it was loaded",
            "A virus flag", "A bit for encryption"), List.of("A bit indicating a page has been modified since it was loaded")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.EASY,
            "Which file system is standard for Windows?", List.of("EXT4", "FAT32", "NTFS", "HFS+"), List.of("NTFS")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is the 'Init' process (PID 1) in Unix?",
            List.of("The first process started by the kernel",
            "A process that kills orphans", "A memory manager", "The login shell"), List.of("The first process started by the kernel")));
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
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.MEDIUM,
            "What is the difference between a process and a thread?",
            List.of("No difference",
            "Threads share memory space while processes have separate memory",
            "Processes are faster", "Threads cannot communicate"), List.of("Threads share memory space while processes have separate memory")));
        q.add(new Question(null, QuestionTopic.SYSTEMS, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the role of the MMU (Memory Management Unit)?",
            List.of("Managing files",
            "Translating virtual addresses to physical addresses and enforcing memory protection",
            "Managing CPU cores", "Handling network packets"), List.of("Translating virtual addresses to physical addresses and enforcing memory protection")));
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
            QuestionDifficulty.MEDIUM,
            "What is the purpose of the 'Client Hello' message in a TLS handshake?",
            List.of("Authenticate the server", "Exchange session keys",
            "Initiate the handshake and specify supported cipher suites",
            "Finalize the encryption"), List.of("Initiate the handshake and specify supported cipher suites")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "Which protocol primarily translates human-readable domain names to IP addresses?",
            List.of("DHCP", "DNS", "ARP", "ICMP"), List.of("DNS")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY, "What does DNS stand for?",
            List.of("Dynamic Network Allocation", "Domain Name System", "Distributed Node Access",
            "Digital Network Architecture"), List.of("Domain Name System")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is the default port for HTTPS?", List.of("80", "443", "22", "8080"), List.of("443")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is a 'socket'?",
            List.of("A physical port", "An endpoint for communication (IP + Port)",
            "A type of cable",
            "A routing table entry"), List.of("An endpoint for communication (IP + Port)")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "Which layer of the OSI model is responsible for routing?",
            List.of("Data Link Layer", "Transport Layer", "Network Layer", "Session Layer"), List.of("Network Layer")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What does TTL (Time to Live) in an IP packet signify?",
            List.of("Expiration time in seconds",
            "The number of hops the packet can take before being discarded",
            "Packet size", "Encryption level"), List.of("The number of hops the packet can take before being discarded")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is the purpose of a DHCP server?", List.of("Translating domain names",
            "Assigning IP addresses automatically to devices", "Routing packets",
            "Storing files"), List.of("Assigning IP addresses automatically to devices")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY,
            "Which of these is a connectionless protocol?", List.of("TCP", "UDP", "SSH", "FTP"), List.of("UDP")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is 'latency' in a network?",
            List.of("Data transfer rate",
            "The time delay for a packet to travel from source to destination",
            "Packet loss frequency", "Encryption speed"), List.of("The time delay for a packet to travel from source to destination")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is BGP (Border Gateway Protocol) used for?",
            List.of("Local area networks",
            "Routing between different autonomous systems on the internet",
            "Assigning private IPs", "Sending emails"), List.of("Routing between different autonomous systems on the internet")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of a Subnet Mask?",
            List.of("Hiding the IP address",
            "Defining the network and host portions of an IP address",
            "Encrypting data", "Allowing remote access"), List.of("Defining the network and host portions of an IP address")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, "What does ICMP stand for?",
            List.of("Internet Control Message Protocol", "Internal Communication Management Port",
            "Instant Connection Message Protocol",
            "Internet Cache Management Protocol"), List.of("Internet Control Message Protocol")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "A 'Three-way Handshake' is used by which protocol?",
            List.of("UDP", "TCP", "ICMP", "IP"), List.of("TCP")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY,
            "What is the primary function of a Router?", List.of("Connect many computers in a LAN",
            "Forward data packets between different networks", "Host web pages",
            "Manage user logins"), List.of("Forward data packets between different networks")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, "What is 'bandwidth'?",
            List.of("Delay of data", "The maximum rate of data transfer across a given path",
            "The distance of a network", "The number of devices connected"), List.of("The maximum rate of data transfer across a given path")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the difference between IPv4 and IPv6?", List.of("IPv6 is 32-bit",
            "IPv6 uses 128-bit addresses", "IPv4 is more secure",
            "IPv6 has fewer addresses"), List.of("IPv6 uses 128-bit addresses")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, "What is MAC address?",
            List.of("An address for Apple computers",
            "A unique physical identifier for a network interface",
            "A software IP address", "A security password"), List.of("A unique physical identifier for a network interface")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of a CDN (Content Delivery Network)?",
            List.of("Store source code", "Distribute content closer to users for faster access",
            "Back up databases", "Provide email services"), List.of("Distribute content closer to users for faster access")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EASY,
            "What tool is commonly used to test connectivity to a host?",
            List.of("SSH", "Ping", "Telnet", "FTP"), List.of("Ping")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is an 'Anycast' address?",
            List.of("Sent to all nodes", "Sent to a single specific node",
            "Sent to the closest node in a group",
            "Sent to a random node"), List.of("Sent to the closest node in a group")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Why do we use Ports in networking?",
            List.of("To speed up the connection",
            "To allow multiple network applications to run on one device",
            "To hide the IP address", "To physicalize the network"), List.of("To allow multiple network applications to run on one device")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is QUIC protocol and why was it developed?",
            List.of("A replacement for DNS",
            "A UDP-based transport protocol designed to reduce latency and improve HTTP/3 performance",
            "A security protocol", "A file transfer protocol"), List.of("A UDP-based transport protocol designed to reduce latency and improve HTTP/3 performance")));
        q.add(new Question(null, QuestionTopic.NETWORKING, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the purpose of the SYN-ACK-ACK in TCP's three-way handshake?",
            List.of("Data transfer",
            "Establishing a synchronized connection and confirming both parties can send and receive",
            "Closing a connection", "Error checking"), List.of("Establishing a synchronized connection and confirming both parties can send and receive")));
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
            QuestionDifficulty.EXTREME, "What is TCP slow start?",
            List.of("A connection delay",
            "A congestion control mechanism that gradually increases transmission rate",
            "A security feature", "A timeout mechanism"), List.of("A congestion control mechanism that gradually increases transmission rate")));
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
        return q;
    }

    private List<Question> getDatabaseQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.SELECT_ALL, QuestionDifficulty.MEDIUM, null,
            "Select ALL the properties that refer to ACID in database transactions:",
            List.of("Atomicity", "Asynchronous", "Consistency", "Concurrency", "Isolation", "Immutability", "Durability"), 
            List.of("Atomicity", "Consistency", "Isolation", "Durability")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "When can an index negatively impact performance?", List.of("During SELECT queries",
            "During INSERT or UPDATE operations", "When reading data",
            "During joins"), List.of("During INSERT or UPDATE operations")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What isolation level prevents dirty reads but allows non-repeatable reads?",
            List.of("Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable"), List.of("Read Committed")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What does the 'I' in ACID stand for?",
            List.of("Integrity", "Iteration", "Isolation", "Immutability"), List.of("Isolation")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a common reason to choose a NoSQL database over a relational one?",
            List.of("Strong consistency is required",
            "Need for flexible schema or horizontal scalability",
            "Complex joins are frequent", "SQL is too modern"), List.of("Need for flexible schema or horizontal scalability")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, "What does SQL stand for?",
            List.of("Simple Query Language", "Structured Query Language", "Standard Query Level",
            "Sequential Query Library"), List.of("Structured Query Language")));
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
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.EASY,
            "Which command is used to add new data to a table?",
            List.of("ADD", "INSERT", "UPDATE", "CREATE"), List.of("INSERT")));
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
            "What does the 'atomicity' in ACID ensure?",
            List.of("Data is stored as atoms",
            "A transaction is treated as a single unit, which either succeeds completely or fails completely",
            "Data is constant", "Transactions are isolated"), List.of("A transaction is treated as a single unit, which either succeeds completely or fails completely")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is Sharding?",
            List.of("Deleting rows",
            "Splitting a large database into smaller, faster, more easily managed parts called shards",
            "Encrypting data", "Adding more CPUs"), List.of("Splitting a large database into smaller, faster, more easily managed parts called shards")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the difference between INNER JOIN and LEFT JOIN?",
            List.of("No difference",
            "INNER JOIN returns matching rows; LEFT JOIN returns all rows from the left table and matching from the right",
            "INNER JOIN is faster", "LEFT JOIN is for NoSQL"), List.of("INNER JOIN returns matching rows; LEFT JOIN returns all rows from the left table and matching from the right")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is a Stored Procedure?",
            List.of("A manual for DBAs", "A prepared SQL code that you can save and reuse",
            "A type of database",
            "An error message"), List.of("A prepared SQL code that you can save and reuse")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What does the CAP theorem state?",
            List.of("Consistency, Availability, and Performance",
            "Consistency, Availability, and Partition Tolerance - pick two",
            "Capacity, Availability, and Partitions",
            "Caching, Availability, and Performance"), List.of("Consistency, Availability, and Partition Tolerance - pick two")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is 'Write Ahead Logging' (WAL)?",
            List.of("Writing logs after a crash",
            "Ensuring data changes are recorded in a log before being applied to the database",
            "Logging web requests", "Writing to disk directly"), List.of("Ensuring data changes are recorded in a log before being applied to the database")));
        q.add(new Question(
            null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.EASY,
            "What is a Primary Key?",
            List.of("A common key",
            "A unique identifier for each record in a table", "An optional field",
            "A foreign key"), List.of("A unique identifier for each record in a table")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of the HAVING clause?",
            List.of("Filter rows", "Filter groups created by GROUP BY", "Sort results",
            "Join tables"), List.of("Filter groups created by GROUP BY")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is Vertical Scaling in databases?",
            List.of("Adding more servers", "Adding more power (CPU, RAM) to an existing server",
            "Adding more tables", "Adding more rows"), List.of("Adding more power (CPU, RAM) to an existing server")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, "What is a Database Schema?",
            List.of("The physical server", "The logical structure of the database",
            "A list of users",
            "The backup plan"), List.of("The logical structure of the database")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "Which NoSQL database type is best for social network relationships?",
            List.of("Document store", "Key-value store", "Graph database", "Column-family store"), List.of("Graph database")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is the 'phantom read' problem?",
            List.of("Reading deleted data",
            "A transaction sees new rows added by another transaction that wasn't there before",
            "Reading uncommitted data", "An error during read"), List.of("A transaction sees new rows added by another transaction that wasn't there before")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is MVCC (Multi-Version Concurrency Control)?",
            List.of("A backup system",
            "A technique that keeps multiple versions of data to allow concurrent reads and writes without locking",
            "A replication method", "A query optimizer"), List.of("A technique that keeps multiple versions of data to allow concurrent reads and writes without locking")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is a clustered index?",
            List.of("An index on multiple columns",
            "An index where the leaf nodes contain the actual data rows, sorted by the index key",
            "An index shared across servers", "A backup index"), List.of("An index where the leaf nodes contain the actual data rows, sorted by the index key")));
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
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the difference between hot, warm, and cold data in database tiering?",
            List.of("Temperature of servers",
            "Frequency of access: hot is frequently accessed, cold is rarely accessed",
            "Data type classification", "Encryption levels"), List.of("Frequency of access: hot is frequently accessed, cold is rarely accessed")));
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
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is a write-ahead log (WAL) checkpoint?",
            List.of("A backup point",
            "A process that flushes dirty pages to disk and marks what has been persisted",
            "A transaction start", "A schema change"), List.of("A process that flushes dirty pages to disk and marks what has been persisted")));
        q.add(new Question(null, QuestionTopic.DATABASES, QuestionType.MULTIPLE_CHOICE, QuestionDifficulty.HARD,
            "What is connection pooling?",
            List.of("Sharing internet connections",
            "Reusing database connections to reduce overhead of creating new connections",
            "A backup strategy", "Load balancing"), List.of("Reusing database connections to reduce overhead of creating new connections")));
        return q;
    }

    private List<Question> getConcurrencyQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is a race condition?",
            List.of("Threads competing for CPU time",
            "Multiple threads accessing shared data unsafely",
            "Dead threads", "Infinite loops"), List.of("Multiple threads accessing shared data unsafely")));
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
            QuestionDifficulty.EASY,
            "Which keyword is used to ensure only one thread can access a method at a time in Java?",
            List.of("locked", "private", "synchronized", "exclusive"), List.of("synchronized")));
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
            QuestionDifficulty.BASIC, "What is a 'Semaphore'?",
            List.of("A type of variable",
            "A variable or abstract data type used to control access to a common resource",
            "A network packet", "A sorting algorithm"), List.of("A variable or abstract data type used to control access to a common resource")));
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
            QuestionDifficulty.EASY,
            "What is a 'Daemon thread'?",
            List.of("A thread that runs in the background", "A malicious thread",
            "A thread that prevents the JVM from exiting",
            "A high-priority thread"), List.of("A thread that runs in the background")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the purpose of 'CountDownLatch' in Java?",
            List.of("A counter for loops",
            "A synchronization aid that allows one or more threads to wait until a set of operations completes",
            "A type of array", "A lock for variables"), List.of("A synchronization aid that allows one or more threads to wait until a set of operations completes")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD, "What is 'Livelock'?",
            List.of("A crash",
            "Threads constantly change state in response to each other without making progress",
            "A thread that stays alive forever", "A memory overflow"), List.of("Threads constantly change state in response to each other without making progress")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "Which class in Java is used for atomic integer operations?",
            List.of("Integer", "AtomicInteger", "VolatileInteger", "ThreadSafeInt"), List.of("AtomicInteger")));
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
            QuestionDifficulty.BASIC,
            "What does 'thread safety' mean?",
            List.of("The thread is protected by a firewall",
            "The code functions correctly during simultaneous execution by multiple threads",
            "Only one thread can run at a time", "The threads use safe memory"), List.of("The code functions correctly during simultaneous execution by multiple threads")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'Barrier' in concurrency?",
            List.of("A security wall",
            "A synchronization point where multiple threads must wait until all threads reach it",
            "A crashed thread", "A network gateway"), List.of("A synchronization point where multiple threads must wait until all threads reach it")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is a 'Future' in Java concurrency?",
            List.of("A prediction of bugs", "The result of an asynchronous computation",
            "A variable that will be created later", "A high-priority thread"), List.of("The result of an asynchronous computation")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, "What is 'Parallelism'?",
            List.of("Running tasks one after another", "Simultaneous execution of multiple tasks",
            "Using multiple screens", "Writing code in parallel lines"), List.of("Simultaneous execution of multiple tasks")));
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
            QuestionDifficulty.EXTREME,
            "What is the happens-before relationship in Java Memory Model?",
            List.of("Temporal ordering",
            "A guarantee that memory writes by one thread are visible to another thread",
            "Thread priority",
            "Execution order"), List.of("A guarantee that memory writes by one thread are visible to another thread")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is a lock-free data structure?",
            List.of("A structure without any synchronization",
            "A structure that guarantees system-wide progress without using locks",
            "A read-only structure",
            "An immutable structure"), List.of("A structure that guarantees system-wide progress without using locks")));
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
            QuestionDifficulty.EXTREME,
            "What is the difference between mutual exclusion and critical section?",
            List.of("No difference",
            "Critical section is the code region; mutual exclusion is the property ensuring only one thread executes it",
            "Mutual exclusion is faster", "Critical section uses more memory"), List.of("Critical section is the code region; mutual exclusion is the property ensuring only one thread executes it")));
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
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the Producer-Consumer problem?",
            List.of("A supply chain issue",
            "A classic synchronization problem where producers add items to a buffer and consumers remove them",
            "A naming convention", "A design pattern for factories"), List.of("A classic synchronization problem where producers add items to a buffer and consumers remove them")));
        q.add(new Question(null, QuestionTopic.CONCURRENCY, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is a CyclicBarrier in Java?",
            List.of("A loop construct",
            "A synchronization aid that allows threads to wait at a barrier point and optionally run a barrier action",
            "A type of lock", "A thread pool"), List.of("A synchronization aid that allows threads to wait at a barrier point and optionally run a barrier action")));
        return q;
    }

    private List<Question> getSoftwareDesignQuestions() {
        List<Question> q = new ArrayList<>();
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
            QuestionDifficulty.BASIC,
            "In SOLID principles, what does the 'S' stand for?",
            List.of("Single Execution", "Static Binding", "Single Responsibility Principle",
            "System Design"), List.of("Single Responsibility Principle")));
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
            QuestionDifficulty.BASIC,
            "What is the DRY principle?",
            List.of("Don't Repeat Yourself", "Do Repeat Yourself", "Digital Realignment Yearly",
            "Data Recovery Yield"), List.of("Don't Repeat Yourself")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Liskov Substitution Principle?",
            List.of("Subclasses should be able to replace their base classes without affecting correctness",
            "Every class must have an interface", "Don't use subclasses",
            "Use substitution for speed"), List.of("Subclasses should be able to replace their base classes without affecting correctness")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'Singleton' pattern?",
            List.of("A pattern for making many objects",
            "Ensures a class has only one instance and provides a global point of access to it",
            "A type of array", "A single-threaded application"), List.of("Ensures a class has only one instance and provides a global point of access to it")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is a 'Design Pattern'?",
            List.of("A graphic design",
            "A general repeatable solution to a commonly occurring problem in software design",
            "A syntax rule", "A hardware specification"), List.of("A general repeatable solution to a commonly occurring problem in software design")));
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
            QuestionDifficulty.HARD,
            "What is the 'Dependency Inversion' principle?",
            List.of("Inverting the order of methods",
            "High-level modules should not depend on low-level modules; both should depend on abstractions",
            "Injecting dependencies at runtime only", "Removing all dependencies"), List.of("High-level modules should not depend on low-level modules; both should depend on abstractions")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is a 'Domain Model'?",
            List.of("A website template",
            "A conceptual model of the domain that incorporates both behavior and data",
            "A type of database", "A security layer"), List.of("A conceptual model of the domain that incorporates both behavior and data")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC,
            "What is 'Polymorphism'?",
            List.of("Creating many files", "The ability of an object to take on many forms",
            "Using many processors", "Writing code in multiple languages"), List.of("The ability of an object to take on many forms")));
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
            "What is 'Inheritance'?",
            List.of("Getting money from someone",
            "A mechanism where a new class is derived from an existing class",
            "Copying a file", "Sharing a database"), List.of("A mechanism where a new class is derived from an existing class")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the 'Command' pattern?",
            List.of("Using a terminal",
            "Encapsulating a request as an object, thereby letting you parameterize clients with different requests",
            "Giving orders to the CPU", "A type of script"), List.of("Encapsulating a request as an object, thereby letting you parameterize clients with different requests")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is Domain-Driven Design (DDD)?",
            List.of("A UI framework",
            "An approach to software development that centers the design on the core domain and domain logic",
            "A database design method", "A testing strategy"), List.of("An approach to software development that centers the design on the core domain and domain logic")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is the Hexagonal Architecture (Ports and Adapters)?",
            List.of("A physical server layout",
            "An architectural pattern that isolates the core logic from external concerns using ports and adapters",
            "A database schema", "A network topology"), List.of("An architectural pattern that isolates the core logic from external concerns using ports and adapters")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Builder pattern used for?",
            List.of("Building servers",
            "Constructing complex objects step by step, allowing different representations",
            "Compiling code", "Managing dependencies"), List.of("Constructing complex objects step by step, allowing different representations")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is the Adapter pattern?",
            List.of("A power converter",
            "A pattern that allows incompatible interfaces to work together",
            "A database connector", "A thread wrapper"), List.of("A pattern that allows incompatible interfaces to work together")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is CQRS (Command Query Responsibility Segregation)?",
            List.of("A database type",
            "A pattern that separates read and write operations into different models",
            "A security protocol", "A testing framework"), List.of("A pattern that separates read and write operations into different models")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Prototype pattern?", List.of("Initial code version",
            "Creating new objects by cloning an existing object", "A testing mock",
            "A first draft design"), List.of("Creating new objects by cloning an existing object")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM,
            "What is cohesion in software design?",
            List.of("Gluing modules together",
            "The degree to which elements of a module belong together",
            "Code duplication", "Inheritance depth"), List.of("The degree to which elements of a module belong together")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.EXTREME,
            "What is Event Sourcing?",
            List.of("Logging events",
            "Storing all changes to application state as a sequence of events",
            "Event handling in UI", "A messaging protocol"), List.of("Storing all changes to application state as a sequence of events")));
        q.add(new Question(null, QuestionTopic.SOFTWARE_DESIGN, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD,
            "What is the Chain of Responsibility pattern?",
            List.of("A management hierarchy",
            "A pattern where a request is passed along a chain of handlers until one handles it",
            "Error handling", "A linked list"), List.of("A pattern where a request is passed along a chain of handlers until one handles it")));
        return q;
    }

    private List<Question> getLanguageKnowledgeQuestions() {
        List<Question> q = new ArrayList<>();
        
        // JAVA
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

        // PYTHON
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.BASIC, UserLanguage.PYTHON,
            "What replaces the concept of a 'null' typical in other languages in Python?",
            List.of("undefined", "None", "Null", "NaN"), List.of("None")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.EASY, UserLanguage.PYTHON,
            "Fill in the blank: In Python, you define a function using the ____ keyword.",
            List.of(), List.of("def", "DEF")));

        // JAVASCRIPT
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

        // C++
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD, UserLanguage.C_PLUS_PLUS,
            "In C++, what does the 'virtual' keyword inside a base class method declaration do?",
            List.of("Makes the method private", "Allows the method to be overridden in derived classes via late binding", "Prevents the method from being inherited", "Forces the method to be inline"), List.of("Allows the method to be overridden in derived classes via late binding")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.C_PLUS_PLUS,
            "Fill in the blank: The standard input stream object in C++ is called ____.",
            List.of(), List.of("cin", "std::cin")));

        // C#
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.MEDIUM, UserLanguage.C_SHARP,
            "In C#, which keyword is used to handle exceptions?",
            List.of("try...catch", "except", "throws", "rescue"), List.of("try...catch")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.HARD, UserLanguage.C_SHARP,
            "Fill in the blank: In C#, Language Integrated Query is commonly abbreviated as ____.",
            List.of(), List.of("LINQ", "linq", "Linq")));

        // GO
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.MULTIPLE_CHOICE,
            QuestionDifficulty.HARD, UserLanguage.GO,
            "How do you declare a public function stored in a Go package?",
            List.of("Use the 'public' keyword", "Start the function name with a capital letter", "Use the '@export' decorator", "Functions are always public in Go"), List.of("Start the function name with a capital letter")));
        q.add(new Question(null, QuestionTopic.LANGUAGE_KNOWLEDGE, QuestionType.FILL_IN_THE_BLANK,
            QuestionDifficulty.MEDIUM, UserLanguage.GO,
            "Fill in the blank: In Go, lightweight threads managed by the Go runtime are called ____.",
            List.of(), List.of("goroutines", "Goroutines", "goroutine")));

        return q;
    }
}
