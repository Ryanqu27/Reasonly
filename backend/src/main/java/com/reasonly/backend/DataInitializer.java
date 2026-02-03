package com.reasonly.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.Question.QuestionRepository;
import com.reasonly.backend.Question.QuestionType;

@Configuration
public class DataInitializer {

    // Method for testing purposes
    /* 
    @Bean
    CommandLineRunner init(QuestionRepository repository) {
        return args -> {
            if (repository.count() > 0)
                return;
            List<Question> questions = new ArrayList<>();
            questions.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "Which data structure provides average O(1) lookup time?",
                List.of("Array", "Linked List", "Hash Table", "Binary Tree"), "Hash Table"));
            questions.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EASY,
                "Which traversal of a binary search tree outputs sorted values?",
                List.of("Preorder", "Postorder", "Level-order", "Inorder"), "Inorder"));
            questions.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "What happens to time complexity when a recursive algorithm recomputes overlapping subproblems?",
                List.of("Becomes linear", "Becomes exponential", "Remains constant", "Becomes logarithmic"),
                "Becomes exponential"));

            repository.saveAll(questions);
        };
    }
    */

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
            questions.addAll(getDebuggingQuestions());
            questions.addAll(getCodeReasoningQuestions());

            repository.saveAll(questions);
        };
    }

    private List<Question> getDSAQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "Which data structure provides average O(1) lookup time?",
                List.of("Array", "Linked List", "Hash Table", "Binary Tree"), "Hash Table"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EASY,
                "Which traversal of a binary search tree outputs sorted values?",
                List.of("Preorder", "Postorder", "Level-order", "Inorder"), "Inorder"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "What happens to time complexity when a recursive algorithm recomputes overlapping subproblems?",
                List.of("Becomes linear", "Becomes exponential", "Remains constant", "Becomes logarithmic"),
                "Becomes exponential"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "Why does merge sort require additional memory?", List.of("It uses recursion",
                        "It creates temporary arrays", "It swaps elements", "It compares adjacent elements"),
                "It creates temporary arrays"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "What is the primary advantage of a Bloom Filter?",
                List.of("O(1) deletion", "Space efficiency for set membership tests", "Always returns exact results",
                        "Guarantees no false positives"),
                "Space efficiency for set membership tests"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "Which algorithm is most commonly used for finding the shortest path in a weighted graph without negative edges?",
                List.of("Breadth-First Search", "Depth-First Search", "Dijkstra's Algorithm", "Kruskal's Algorithm"),
                "Dijkstra's Algorithm"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "What is the time complexity of searching for an element in a balanced Binary Search Tree?",
                List.of("O(1)", "O(log n)", "O(n)", "O(n log n)"), "O(log n)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "Which data structure is typically used to implement a LIFO (Last In, First Out) behavior?",
                List.of("Queue", "Stack", "Priority Queue", "Linked List"), "Stack"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "What is the worst-case time complexity of Quick Sort?",
                List.of("O(n log n)", "O(n^2)", "O(n)", "O(log n)"), "O(n^2)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "Which algorithm is used to find the minimum spanning tree of a graph?",
                List.of("Dijkstra's", "Prim's", "Bellman-Ford", "Floyd-Warshall"), "Prim's"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "What is the main property of a Heap?", List.of("Elements are sorted",
                        "Every node is larger than its children", "It is a full binary tree", "O(1) search time"),
                "Every node is larger than its children"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "Which of these is a stable sorting algorithm?",
                List.of("Quick Sort", "Merge Sort", "Heap Sort", "Selection Sort"), "Merge Sort"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EASY,
                "In a doubly linked list, each node has pointers to how many nodes?",
                List.of("One", "Two", "Three", "It depends"), "Two"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "What is the space complexity of a recursive Depth-First Search on a tree of height h?",
                List.of("O(1)", "O(h)", "O(n)", "O(log n)"), "O(h)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "A Red-Black tree is a type of what?", List.of("Min-heap", "Self-balancing BST", "B-Tree", "Graph"),
                "Self-balancing BST"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "Which sorting algorithm has the best average-case performance?",
                List.of("Bubble Sort", "Merge Sort", "Insertion Sort", "Selection Sort"), "Merge Sort"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EASY,
                "What is the time complexity of inserting an element at the beginning of an array?",
                List.of("O(1)", "O(n)", "O(log n)", "O(1) amortized"), "O(n)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "What is the primary use of a Queue?",
                List.of("LIFO processing", "FIFO processing", "Sorting data", "Storing key-value pairs"),
                "FIFO processing"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "Dynamic Programming is based on which concept?",
                List.of("Greedy selection", "Dividing into smaller subproblems and storing results",
                        "Randomized trials", "Systematic searching"),
                "Dividing into smaller subproblems and storing results"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "Which of these is an NP-complete problem?",
                List.of("Shortest path", "Traveling Salesman Problem", "Sorting an array", "Binary search"),
                "Traveling Salesman Problem"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EASY,
                "What is the height of a balanced tree with n nodes?",
                List.of("O(n)", "O(log n)", "O(sqrt(n))", "O(n^2)"), "O(log n)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "The Kadane's algorithm is used to find what?", List.of("Shortest path", "Maximum subarray sum",
                        "Minimum spanning tree", "Strongly connected components"),
                "Maximum subarray sum"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "What is the time complexity of building a heap from an array of n elements?",
                List.of("O(n log n)", "O(n)", "O(n^2)", "O(log n)"), "O(n)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.BASIC,
                "Which structure is best for implementing a dictionary?",
                List.of("Stack", "Hash Map", "Linked List", "Array"), "Hash Map"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "A circular linked list has no what?", List.of("Nodes", "Pointers", "Null ending", "Values"),
                "Null ending"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EXTREME,
                "What is the amortized time complexity of operations in a Fibonacci heap?",
                List.of("O(log n) for all operations", "O(1) for insert and decrease-key, O(log n) for extract-min",
                        "O(n) for all operations", "O(log log n) for insert"),
                "O(1) for insert and decrease-key, O(log n) for extract-min"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EXTREME,
                "Which data structure is used to efficiently solve the Range Minimum Query problem?",
                List.of("Binary Search Tree", "Sparse Table or Segment Tree", "Hash Table", "Trie"),
                "Sparse Table or Segment Tree"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "What is the time complexity of finding the kth smallest element in a BST?",
                List.of("O(n)", "O(k)", "O(h + k) where h is height", "O(log n)"), "O(h + k) where h is height"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EXTREME,
                "What is a B+ tree's main advantage over a B-tree for databases?",
                List.of("Faster insertions", "All data is stored in leaves, enabling efficient range queries",
                        "Less memory usage", "Simpler implementation"),
                "All data is stored in leaves, enabling efficient range queries"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "What is the purpose of a Trie data structure?",
                List.of("Sorting numbers", "Efficient string prefix matching", "Graph traversal", "Balancing trees"),
                "Efficient string prefix matching"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "What is topological sorting used for?", List.of("Sorting numbers",
                        "Ordering tasks with dependencies in a DAG", "Balancing heaps", "Finding shortest paths"),
                "Ordering tasks with dependencies in a DAG"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EXTREME,
                "What is the time complexity of the Floyd-Warshall algorithm?",
                List.of("O(V + E)", "O(V^2)", "O(V^3)", "O(V * E)"), "O(V^3)"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.MEDIUM,
                "Which algorithm is best for finding strongly connected components?",
                List.of("Dijkstra's", "Tarjan's or Kosaraju's algorithm", "Prim's", "Kruskal's"),
                "Tarjan's or Kosaraju's algorithm"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.HARD,
                "What is the difference between BFS and DFS in terms of memory usage?", List.of("No difference",
                        "BFS uses more memory in wide graphs", "DFS always uses more memory", "Both use O(1) memory"),
                "BFS uses more memory in wide graphs"));
        q.add(new Question(null, QuestionType.DATA_STRUCTURES_AND_ALGORITHMS, QuestionDifficulty.EXTREME,
                "What is an AVL tree's maximum allowed height difference between subtrees?",
                List.of("0", "1", "2", "log n"), "1"));
        return q;
    }

    private List<Question> getSystemsQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.BASIC,
                "What is the primary purpose of virtual memory?",
                List.of("Increase CPU speed", "Allow programs to use more memory than physically available",
                        "Prevent deadlocks", "Store cache data"),
                "Allow programs to use more memory than physically available"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What happens during a context switch?", List.of("CPU executes a new instruction", "Memory is cleared",
                        "CPU state is saved and restored", "A process terminates"),
                "CPU state is saved and restored"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD,
                "Why are system calls generally slower than regular function calls?",
                List.of("They require disk access", "They switch between user and kernel mode", "They flush CPU cache",
                        "They allocate memory"),
                "They switch between user and kernel mode"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is the main difference between interrupts and polling?",
                List.of("Polling is always faster", "Interrupts are hardware only",
                        "Interrupts let the CPU work on other tasks until notified; polling requires constant checking",
                        "Polling is used for high-priority tasks"),
                "Interrupts let the CPU work on other tasks until notified; polling requires constant checking"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD,
                "What does an i-node store in a Unix file system?",
                List.of("The file's name", "The actual file content", "Metadata about a file", "The user's password"),
                "Metadata about a file"));
        q.add(new Question(
                null, QuestionType.SYSTEMS, QuestionDifficulty.BASIC, "What is a kernel?", List.of("The user interface",
                        "The core part of the OS managing resources", "A type of CPU", "A file system"),
                "The core part of the OS managing resources"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is a 'deadlock' in an operating system?",
                List.of("A crashed program", "A state where two processes are stuck waiting for each other",
                        "A memory leak", "A slow network connection"),
                "A state where two processes are stuck waiting for each other"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is the role of a garbage collector?",
                List.of("Deleting unused files", "Reclaiming memory no longer used by the program",
                        "Optimizing CPU cycles", "Scanning for viruses"),
                "Reclaiming memory no longer used by the program"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EASY,
                "Which component manages the execution of processes?",
                List.of("Memory Manager", "Scheduler", "File System", "I/O Manager"), "Scheduler"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD,
                "What is the 'working set' of a process?",
                List.of("All memory it can access", "The set of pages it has actively used recently",
                        "Its total CPU time", "Its open file descriptors"),
                "The set of pages it has actively used recently"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM, "What is thrashing?",
                List.of("High CPU usage", "Excessive paging lead by the OS spending more time swapping than executing",
                        "A hardware failure", "Deleting files rapidly"),
                "Excessive paging lead by the OS spending more time swapping than executing"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.BASIC, "What does BIOS stand for?",
                List.of("Binary Input Output System", "Basic Input Output System", "Better Input Output System",
                        "Basic Internal OS"),
                "Basic Input Output System"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM, "What is a shell?",
                List.of("The hardware casing", "A command-line interpreter for the OS", "A type of virus",
                        "A database engine"),
                "A command-line interpreter for the OS"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD,
                "Which system call is used to create a new process in Unix?",
                List.of("new()", "exec()", "fork()", "spawn()"), "fork()"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EASY, "A 'thread' is often called a what?",
                List.of("Heavyweight process", "Lightweight process", "Kernel task", "Sub-process"),
                "Lightweight process"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is the purpose of an OS page table?", List.of("Store file names",
                        "Map virtual addresses to physical addresses", "Track open processes", "Manage CPU registers"),
                "Map virtual addresses to physical addresses"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD,
                "A 'Segmentation Fault' typically occurs when?", List.of("The CPU overheats",
                        "A program tries to access memory it doesn't own", "The disk is full", "The network is down"),
                "A program tries to access memory it doesn't own"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.BASIC, "What is cache memory?",
                List.of("Slow, high-capacity storage", "Fast, small memory near the CPU", "A backup system",
                        "Virtual memory on disk"),
                "Fast, small memory near the CPU"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "Which CPU scheduling algorithm gives the lowest average waiting time?",
                List.of("First-Come First-Served", "Shortest Job First", "Round Robin", "Priority Scheduling"),
                "Shortest Job First"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD, "What is RAID 0 primarily used for?",
                List.of("Data redundancy", "Mirroring", "Performance (striping)", "Error correction"),
                "Performance (striping)"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.BASIC, "What is an interrupt?",
                List.of("A program crash", "A signal from hardware or software requiring CPU attention",
                        "A network pause", "A user input error"),
                "A signal from hardware or software requiring CPU attention"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is 'dirty' bit in memory management?",
                List.of("Corrupted data", "A bit indicating a page has been modified since it was loaded",
                        "A virus flag", "A bit for encryption"),
                "A bit indicating a page has been modified since it was loaded"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EASY,
                "Which file system is standard for Windows?", List.of("EXT4", "FAT32", "NTFS", "HFS+"), "NTFS"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is the 'Init' process (PID 1) in Unix?", List.of("The first process started by the kernel",
                        "A process that kills orphans", "A memory manager", "The login shell"),
                "The first process started by the kernel"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD, "What is a 'Spinlock'?",
                List.of("A lock that puts processes to sleep",
                        "A lock where a thread actively waits (loops) until the lock is available",
                        "A type of deadlock", "A hardware switch"),
                "A lock where a thread actively waits (loops) until the lock is available"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EXTREME,
                "What is the difference between memory-mapped I/O and port-mapped I/O?",
                List.of("No difference", "Memory-mapped uses the same address space as RAM", "Port-mapped is faster",
                        "Memory-mapped only works on Unix"),
                "Memory-mapped uses the same address space as RAM"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EXTREME,
                "What is a TLB (Translation Lookaside Buffer)?", List.of("A type of RAM",
                        "A cache for virtual-to-physical address translations", "A disk buffer", "A network buffer"),
                "A cache for virtual-to-physical address translations"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD, "What is copy-on-write (COW)?",
                List.of("A backup strategy",
                        "A resource-management technique where copies are made only when modifications occur",
                        "A file system type", "A network protocol"),
                "A resource-management technique where copies are made only when modifications occur"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is the difference between a process and a thread?",
                List.of("No difference", "Threads share memory space while processes have separate memory",
                        "Processes are faster", "Threads cannot communicate"),
                "Threads share memory space while processes have separate memory"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EXTREME,
                "What is the role of the MMU (Memory Management Unit)?",
                List.of("Managing files",
                        "Translating virtual addresses to physical addresses and enforcing memory protection",
                        "Managing CPU cores", "Handling network packets"),
                "Translating virtual addresses to physical addresses and enforcing memory protection"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.HARD, "What is a memory barrier/fence?",
                List.of("Physical memory protection", "An instruction that prevents memory reordering across it",
                        "A firewall for RAM", "A type of cache"),
                "An instruction that prevents memory reordering across it"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM,
                "What is swapping in operating systems?",
                List.of("Exchanging CPUs", "Moving processes between main memory and disk", "Changing file permissions",
                        "Switching network adapters"),
                "Moving processes between main memory and disk"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.EXTREME,
                "What is the difference between hard and soft real-time systems?",
                List.of("Hard systems are more expensive",
                        "Hard systems have strict deadlines; missing them causes failure. Soft systems have flexible deadlines",
                        "Soft systems are faster", "No difference"),
                "Hard systems have strict deadlines; missing them causes failure. Soft systems have flexible deadlines"));
        q.add(new Question(null, QuestionType.SYSTEMS, QuestionDifficulty.MEDIUM, "What is a zombie process?",
                List.of("A malicious process", "A terminated process whose exit status hasn't been read by its parent",
                        "A process with high CPU usage", "A process waiting for I/O"),
                "A terminated process whose exit status hasn't been read by its parent"));
        return q;
    }

    private List<Question> getNetworkingQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC,
                "Which protocol guarantees reliable data delivery?", List.of("UDP", "IP", "TCP", "HTTP"), "TCP"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "Why does HTTP/2 improve performance over HTTP/1.1?",
                List.of("Larger packets", "Binary encoding and multiplexing", "More DNS lookups", "No headers"),
                "Binary encoding and multiplexing"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "What problem does congestion control solve?", List.of("Packet loss due to encryption",
                        "Network overload", "Slow DNS resolution", "IP address exhaustion"),
                "Network overload"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "What is the purpose of the 'Client Hello' message in a TLS handshake?",
                List.of("Authenticate the server", "Exchange session keys",
                        "Initiate the handshake and specify supported cipher suites", "Finalize the encryption"),
                "Initiate the handshake and specify supported cipher suites"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC,
                "Which protocol primarily translates human-readable domain names to IP addresses?",
                List.of("DHCP", "DNS", "ARP", "ICMP"), "DNS"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EASY, "What does DNS stand for?",
                List.of("Dynamic Network Allocation", "Domain Name System", "Distributed Node Access",
                        "Digital Network Architecture"),
                "Domain Name System"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC,
                "What is the default port for HTTPS?", List.of("80", "443", "22", "8080"), "443"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM, "What is a 'socket'?",
                List.of("A physical port", "An endpoint for communication (IP + Port)", "A type of cable",
                        "A routing table entry"),
                "An endpoint for communication (IP + Port)"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "Which layer of the OSI model is responsible for routing?",
                List.of("Data Link Layer", "Transport Layer", "Network Layer", "Session Layer"), "Network Layer"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "What does TTL (Time to Live) in an IP packet signify?",
                List.of("Expiration time in seconds", "The number of hops the packet can take before being discarded",
                        "Packet size", "Encryption level"),
                "The number of hops the packet can take before being discarded"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC,
                "What is the purpose of a DHCP server?", List.of("Translating domain names",
                        "Assigning IP addresses automatically to devices", "Routing packets", "Storing files"),
                "Assigning IP addresses automatically to devices"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EASY,
                "Which of these is a connectionless protocol?", List.of("TCP", "UDP", "SSH", "FTP"), "UDP"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM, "What is 'latency' in a network?",
                List.of("Data transfer rate", "The time delay for a packet to travel from source to destination",
                        "Packet loss frequency", "Encryption speed"),
                "The time delay for a packet to travel from source to destination"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "What is BGP (Border Gateway Protocol) used for?",
                List.of("Local area networks", "Routing between different autonomous systems on the internet",
                        "Assigning private IPs", "Sending emails"),
                "Routing between different autonomous systems on the internet"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "What is the purpose of a Subnet Mask?",
                List.of("Hiding the IP address", "Defining the network and host portions of an IP address",
                        "Encrypting data", "Allowing remote access"),
                "Defining the network and host portions of an IP address"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC, "What does ICMP stand for?",
                List.of("Internet Control Message Protocol", "Internal Communication Management Port",
                        "Instant Connection Message Protocol", "Internet Cache Management Protocol"),
                "Internet Control Message Protocol"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "A 'Three-way Handshake' is used by which protocol?", List.of("UDP", "TCP", "ICMP", "IP"), "TCP"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EASY,
                "What is the primary function of a Router?", List.of("Connect many computers in a LAN",
                        "Forward data packets between different networks", "Host web pages", "Manage user logins"),
                "Forward data packets between different networks"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM, "What is 'bandwidth'?",
                List.of("Delay of data", "The maximum rate of data transfer across a given path",
                        "The distance of a network", "The number of devices connected"),
                "The maximum rate of data transfer across a given path"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "What is the difference between IPv4 and IPv6?", List.of("IPv6 is 32-bit",
                        "IPv6 uses 128-bit addresses", "IPv4 is more secure", "IPv6 has fewer addresses"),
                "IPv6 uses 128-bit addresses"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.BASIC, "What is MAC address?",
                List.of("An address for Apple computers", "A unique physical identifier for a network interface",
                        "A software IP address", "A security password"),
                "A unique physical identifier for a network interface"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "What is the purpose of a CDN (Content Delivery Network)?",
                List.of("Store source code", "Distribute content closer to users for faster access",
                        "Back up databases", "Provide email services"),
                "Distribute content closer to users for faster access"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EASY,
                "What tool is commonly used to test connectivity to a host?", List.of("SSH", "Ping", "Telnet", "FTP"),
                "Ping"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD, "What is an 'Anycast' address?",
                List.of("Sent to all nodes", "Sent to a single specific node", "Sent to the closest node in a group",
                        "Sent to a random node"),
                "Sent to the closest node in a group"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "Why do we use Ports in networking?",
                List.of("To speed up the connection", "To allow multiple network applications to run on one device",
                        "To hide the IP address", "To physicalize the network"),
                "To allow multiple network applications to run on one device"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EXTREME,
                "What is QUIC protocol and why was it developed?",
                List.of("A replacement for DNS",
                        "A UDP-based transport protocol designed to reduce latency and improve HTTP/3 performance",
                        "A security protocol", "A file transfer protocol"),
                "A UDP-based transport protocol designed to reduce latency and improve HTTP/3 performance"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EXTREME,
                "What is the purpose of the SYN-ACK-ACK in TCP's three-way handshake?",
                List.of("Data transfer",
                        "Establishing a synchronized connection and confirming both parties can send and receive",
                        "Closing a connection", "Error checking"),
                "Establishing a synchronized connection and confirming both parties can send and receive"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "What is NAT (Network Address Translation)?",
                List.of("A security protocol", "A method of mapping private IP addresses to public IP addresses",
                        "A routing algorithm", "A type of firewall"),
                "A method of mapping private IP addresses to public IP addresses"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM,
                "What is the difference between a hub and a switch?",
                List.of("No difference", "A switch sends data only to the intended recipient; a hub broadcasts to all",
                        "A hub is faster", "A switch uses wireless"),
                "A switch sends data only to the intended recipient; a hub broadcasts to all"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EXTREME, "What is TCP slow start?",
                List.of("A connection delay",
                        "A congestion control mechanism that gradually increases transmission rate",
                        "A security feature", "A timeout mechanism"),
                "A congestion control mechanism that gradually increases transmission rate"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD,
                "What is ARP (Address Resolution Protocol) used for?", List.of("Resolving domain names",
                        "Mapping IP addresses to MAC addresses", "Routing packets", "Encrypting data"),
                "Mapping IP addresses to MAC addresses"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.MEDIUM, "What is a VPN?",
                List.of("A type of virus", "A secure tunnel for transmitting data over public networks",
                        "A network adapter", "A wireless protocol"),
                "A secure tunnel for transmitting data over public networks"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.EXTREME,
                "What is the difference between flow control and congestion control?",
                List.of("No difference",
                        "Flow control manages sender speed for receiver; congestion control manages network capacity",
                        "Congestion control is for local networks", "Flow control uses UDP"),
                "Flow control manages sender speed for receiver; congestion control manages network capacity"));
        q.add(new Question(null, QuestionType.NETWORKING, QuestionDifficulty.HARD, "What is a reverse proxy?",
                List.of("A proxy that blocks websites",
                        "A server that sits in front of web servers and forwards client requests", "A VPN alternative",
                        "A firewall type"),
                "A server that sits in front of web servers and forwards client requests"));
        return q;
    }

    private List<Question> getDatabaseQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC,
                "What does an index primarily improve?",
                List.of("Insert speed", "Delete speed", "Query lookup speed", "Transaction safety"),
                "Query lookup speed"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "When can an index negatively impact performance?", List.of("During SELECT queries",
                        "During INSERT or UPDATE operations", "When reading data", "During joins"),
                "During INSERT or UPDATE operations"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD,
                "What isolation level prevents dirty reads but allows non-repeatable reads?",
                List.of("Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable"), "Read Committed"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC,
                "What does the 'I' in ACID stand for?", List.of("Integrity", "Iteration", "Isolation", "Immutability"),
                "Isolation"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "What is a common reason to choose a NoSQL database over a relational one?",
                List.of("Strong consistency is required", "Need for flexible schema or horizontal scalability",
                        "Complex joins are frequent", "SQL is too modern"),
                "Need for flexible schema or horizontal scalability"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC, "What does SQL stand for?",
                List.of("Simple Query Language", "Structured Query Language", "Standard Query Level",
                        "Sequential Query Library"),
                "Structured Query Language"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM, "What is a Foreign Key?",
                List.of("A key from another planet",
                        "A field in one table that uniquely identifies a row of another table",
                        "A primary key in the current table", "An encrypted column"),
                "A field in one table that uniquely identifies a row of another table"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM, "What is Database Normalization?",
                List.of("Deleting old data", "Organizing data to reduce redundancy and improve integrity",
                        "Increasing database speed via hardware", "Writing flat files"),
                "Organizing data to reduce redundancy and improve integrity"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is a 'deadlock' in a database?",
                List.of("A forgotten password", "Two transactions waiting for locks held by each other",
                        "A corrupted index", "An empty database"),
                "Two transactions waiting for locks held by each other"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.EASY,
                "Which command is used to add new data to a table?", List.of("ADD", "INSERT", "UPDATE", "CREATE"),
                "INSERT"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "What is the purpose of the GROUP BY clause?", List.of("Sort the results",
                        "Arrange identical data into groups", "Filter individual rows", "Combine tables"),
                "Arrange identical data into groups"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is a 'covering index'?",
                List.of("An index that covers the entire disk", "An index that includes all columns needed for a query",
                        "A primary key", "A backup index"),
                "An index that includes all columns needed for a query"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC, "What is a View in a database?",
                List.of("A picture of the schema", "A virtual table based on the result-set of an SQL statement",
                        "A user interface", "A type of table"),
                "A virtual table based on the result-set of an SQL statement"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "What does the 'atomicity' in ACID ensure?",
                List.of("Data is stored as atoms",
                        "A transaction is treated as a single unit, which either succeeds completely or fails completely",
                        "Data is constant", "Transactions are isolated"),
                "A transaction is treated as a single unit, which either succeeds completely or fails completely"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is Sharding?",
                List.of("Deleting rows",
                        "Splitting a large database into smaller, faster, more easily managed parts called shards",
                        "Encrypting data", "Adding more CPUs"),
                "Splitting a large database into smaller, faster, more easily managed parts called shards"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "What is the difference between INNER JOIN and LEFT JOIN?",
                List.of("No difference",
                        "INNER JOIN returns matching rows; LEFT JOIN returns all rows from the left table and matching from the right",
                        "INNER JOIN is faster", "LEFT JOIN is for NoSQL"),
                "INNER JOIN returns matching rows; LEFT JOIN returns all rows from the left table and matching from the right"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC, "What is a Stored Procedure?",
                List.of("A manual for DBAs", "A prepared SQL code that you can save and reuse", "A type of database",
                        "An error message"),
                "A prepared SQL code that you can save and reuse"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM, "What does the CAP theorem state?",
                List.of("Consistency, Availability, and Performance",
                        "Consistency, Availability, and Partition Tolerance - pick two",
                        "Capacity, Availability, and Partitions", "Caching, Availability, and Performance"),
                "Consistency, Availability, and Partition Tolerance - pick two"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD,
                "What is 'Write Ahead Logging' (WAL)?",
                List.of("Writing logs after a crash",
                        "Ensuring data changes are recorded in a log before being applied to the database",
                        "Logging web requests", "Writing to disk directly"),
                "Ensuring data changes are recorded in a log before being applied to the database"));
        q.add(new Question(
                null, QuestionType.DATABASES, QuestionDifficulty.EASY, "What is a Primary Key?", List.of("A common key",
                        "A unique identifier for each record in a table", "An optional field", "A foreign key"),
                "A unique identifier for each record in a table"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "What is the purpose of the HAVING clause?",
                List.of("Filter rows", "Filter groups created by GROUP BY", "Sort results", "Join tables"),
                "Filter groups created by GROUP BY"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD,
                "What is Vertical Scaling in databases?",
                List.of("Adding more servers", "Adding more power (CPU, RAM) to an existing server",
                        "Adding more tables", "Adding more rows"),
                "Adding more power (CPU, RAM) to an existing server"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.BASIC, "What is a Database Schema?",
                List.of("The physical server", "The logical structure of the database", "A list of users",
                        "The backup plan"),
                "The logical structure of the database"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM,
                "Which NoSQL database type is best for social network relationships?",
                List.of("Document store", "Key-value store", "Graph database", "Column-family store"),
                "Graph database"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is the 'phantom read' problem?",
                List.of("Reading deleted data",
                        "A transaction sees new rows added by another transaction that wasn't there before",
                        "Reading uncommitted data", "An error during read"),
                "A transaction sees new rows added by another transaction that wasn't there before"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.EXTREME,
                "What is MVCC (Multi-Version Concurrency Control)?",
                List.of("A backup system",
                        "A technique that keeps multiple versions of data to allow concurrent reads and writes without locking",
                        "A replication method", "A query optimizer"),
                "A technique that keeps multiple versions of data to allow concurrent reads and writes without locking"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.EXTREME, "What is a clustered index?",
                List.of("An index on multiple columns",
                        "An index where the leaf nodes contain the actual data rows, sorted by the index key",
                        "An index shared across servers", "A backup index"),
                "An index where the leaf nodes contain the actual data rows, sorted by the index key"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is database partitioning?",
                List.of("Deleting old records",
                        "Dividing a database into distinct independent parts to improve performance", "Encrypting data",
                        "Creating backups"),
                "Dividing a database into distinct independent parts to improve performance"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM, "What is a trigger in a database?",
                List.of("A button in the UI",
                        "A stored procedure that automatically executes in response to certain events",
                        "A type of index", "A backup mechanism"),
                "A stored procedure that automatically executes in response to certain events"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.EXTREME,
                "What is the difference between hot, warm, and cold data in database tiering?",
                List.of("Temperature of servers",
                        "Frequency of access: hot is frequently accessed, cold is rarely accessed",
                        "Data type classification", "Encryption levels"),
                "Frequency of access: hot is frequently accessed, cold is rarely accessed"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is replication lag?",
                List.of("Slow queries", "The delay between a write on the primary and when it appears on replicas",
                        "Network latency", "Index rebuild time"),
                "The delay between a write on the primary and when it appears on replicas"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.MEDIUM, "What is a materialized view?",
                List.of("A virtual table", "A view whose results are stored physically and refreshed periodically",
                        "A 3D database", "A temporary table"),
                "A view whose results are stored physically and refreshed periodically"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.EXTREME,
                "What is a write-ahead log (WAL) checkpoint?",
                List.of("A backup point",
                        "A process that flushes dirty pages to disk and marks what has been persisted",
                        "A transaction start", "A schema change"),
                "A process that flushes dirty pages to disk and marks what has been persisted"));
        q.add(new Question(null, QuestionType.DATABASES, QuestionDifficulty.HARD, "What is connection pooling?",
                List.of("Sharing internet connections",
                        "Reusing database connections to reduce overhead of creating new connections",
                        "A backup strategy", "Load balancing"),
                "Reusing database connections to reduce overhead of creating new connections"));
        return q;
    }

    private List<Question> getConcurrencyQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC, "What is a race condition?",
                List.of("Threads competing for CPU time", "Multiple threads accessing shared data unsafely",
                        "Dead threads", "Infinite loops"),
                "Multiple threads accessing shared data unsafely"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "Which condition is required for a deadlock?",
                List.of("Preemption", "Mutual exclusion", "Parallelism", "Caching"), "Mutual exclusion"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD,
                "Why can fine-grained locking improve performance?",
                List.of("It increases lock contention", "It reduces context switches", "It limits the scope of locking",
                        "It prevents race conditions entirely"),
                "It limits the scope of locking"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "How does optimistic locking differ from pessimistic locking?",
                List.of("Pessimistic locking is faster",
                        "Optimistic locking assumes conflicts are rare and checks at commit; pessimistic locking locks data upfront",
                        "Optimistic locking never fails", "Pessimistic locking is only for read-only data"),
                "Optimistic locking assumes conflicts are rare and checks at commit; pessimistic locking locks data upfront"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC,
                "What is the effect of the 'volatile' keyword in Java?",
                List.of("Prevents race conditions", "Ensures visibility of changes to other threads",
                        "Locks the variable", "Makes the variable constant"),
                "Ensures visibility of changes to other threads"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM, "What is a 'Thread Pool'?",
                List.of("A collection of dormant threads", "A managed set of reusable threads for executing tasks",
                        "A type of memory segment", "A debugging tool"),
                "A managed set of reusable threads for executing tasks"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD,
                "What is the 'Double-Checked Locking' pattern used for?",
                List.of("Reducing concurrency", "Lazy initialization in a thread-safe manner with minimal overhead",
                        "Detecting deadlocks", "Hashing data"),
                "Lazy initialization in a thread-safe manner with minimal overhead"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.EASY,
                "Which keyword is used to ensure only one thread can access a method at a time in Java?",
                List.of("locked", "private", "synchronized", "exclusive"), "synchronized"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "What is a 'wait-free' algorithm?",
                List.of("An algorithm that never waits for user input",
                        "An algorithm that guarantees every thread makes progress in a finite number of steps",
                        "A single-threaded algorithm", "An algorithm with no loops"),
                "An algorithm that guarantees every thread makes progress in a finite number of steps"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD,
                "What is the 'ABA problem' in lock-free programming?",
                List.of("A memory leak",
                        "A thread sees a value is 'A', then 'B', then 'A' again, and mistakenly assumes nothing changed",
                        "A type of deadlock", "A circular dependency"),
                "A thread sees a value is 'A', then 'B', then 'A' again, and mistakenly assumes nothing changed"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC, "What is a 'Semaphore'?",
                List.of("A type of variable",
                        "A variable or abstract data type used to control access to a common resource",
                        "A network packet", "A sorting algorithm"),
                "A variable or abstract data type used to control access to a common resource"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM, "What is a 'Reentrant Lock'?",
                List.of("A lock that can't be released",
                        "A lock that allows the thread currently holding it to acquire it again without deadlocking",
                        "A lock for files", "A hardware-level lock"),
                "A lock that allows the thread currently holding it to acquire it again without deadlocking"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD, "What is 'Priority Inversion'?",
                List.of("High priority threads running first",
                        "A low-priority task holds a resource needed by a high-priority task",
                        "A thread losing its priority", "Sorting threads by priority"),
                "A low-priority task holds a resource needed by a high-priority task"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.EASY, "What is a 'Daemon thread'?",
                List.of("A thread that runs in the background", "A malicious thread",
                        "A thread that prevents the JVM from exiting", "A high-priority thread"),
                "A thread that runs in the background"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "What is the purpose of 'CountDownLatch' in Java?",
                List.of("A counter for loops",
                        "A synchronization aid that allows one or more threads to wait until a set of operations completes",
                        "A type of array", "A lock for variables"),
                "A synchronization aid that allows one or more threads to wait until a set of operations completes"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD, "What is 'Livelock'?",
                List.of("A crash", "Threads constantly change state in response to each other without making progress",
                        "A thread that stays alive forever", "A memory overflow"),
                "Threads constantly change state in response to each other without making progress"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC,
                "Which class in Java is used for atomic integer operations?",
                List.of("Integer", "AtomicInteger", "VolatileInteger", "ThreadSafeInt"), "AtomicInteger"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM, "What is 'False Sharing'?",
                List.of("Sharing wrong data",
                        "Multiple processors updating variables that reside on the same cache line", "A network error",
                        "Using a global variable unnecessarily"),
                "Multiple processors updating variables that reside on the same cache line"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD, "What is a 'Read-Write Lock'?",
                List.of("A lock that allows only one reader",
                        "A lock that allows multiple concurrent readers but exclusive access for writers",
                        "A lock for a disk drive", "A type of database index"),
                "A lock that allows multiple concurrent readers but exclusive access for writers"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC, "What does 'thread safety' mean?",
                List.of("The thread is protected by a firewall",
                        "The code functions correctly during simultaneous execution by multiple threads",
                        "Only one thread can run at a time", "The threads use safe memory"),
                "The code functions correctly during simultaneous execution by multiple threads"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "What is a 'Barrier' in concurrency?",
                List.of("A security wall",
                        "A synchronization point where multiple threads must wait until all threads reach it",
                        "A crashed thread", "A network gateway"),
                "A synchronization point where multiple threads must wait until all threads reach it"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD,
                "What is a 'Future' in Java concurrency?",
                List.of("A prediction of bugs", "The result of an asynchronous computation",
                        "A variable that will be created later", "A high-priority thread"),
                "The result of an asynchronous computation"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.BASIC, "What is 'Parallelism'?",
                List.of("Running tasks one after another", "Simultaneous execution of multiple tasks",
                        "Using multiple screens", "Writing code in parallel lines"),
                "Simultaneous execution of multiple tasks"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "What is 'Starvation' in concurrency?",
                List.of("A thread dying of low memory",
                        "A thread is perpetually denied necessary resources to process its work",
                        "The system running out of data", "A server shutdown"),
                "A thread is perpetually denied necessary resources to process its work"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD,
                "What is the 'Fork/Join' framework used for?",
                List.of("Connecting databases",
                        "Parallel execution of tasks that can be broken into smaller subtasks recursively",
                        "Merging Git branches", "Joining strings"),
                "Parallel execution of tasks that can be broken into smaller subtasks recursively"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.EXTREME,
                "What is the happens-before relationship in Java Memory Model?",
                List.of("Temporal ordering",
                        "A guarantee that memory writes by one thread are visible to another thread", "Thread priority",
                        "Execution order"),
                "A guarantee that memory writes by one thread are visible to another thread"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.EXTREME,
                "What is a lock-free data structure?",
                List.of("A structure without any synchronization",
                        "A structure that guarantees system-wide progress without using locks", "A read-only structure",
                        "An immutable structure"),
                "A structure that guarantees system-wide progress without using locks"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD, "What is Compare-And-Swap (CAS)?",
                List.of("A sorting algorithm",
                        "An atomic instruction that compares and conditionally updates a memory location",
                        "A garbage collection technique", "A thread scheduling method"),
                "An atomic instruction that compares and conditionally updates a memory location"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM,
                "What is a monitor in concurrency?",
                List.of("A display screen",
                        "A synchronization construct that bundles mutual exclusion and condition variables",
                        "A debugging tool", "A thread pool"),
                "A synchronization construct that bundles mutual exclusion and condition variables"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.EXTREME,
                "What is the difference between mutual exclusion and critical section?",
                List.of("No difference",
                        "Critical section is the code region; mutual exclusion is the property ensuring only one thread executes it",
                        "Mutual exclusion is faster", "Critical section uses more memory"),
                "Critical section is the code region; mutual exclusion is the property ensuring only one thread executes it"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD, "What is a condition variable?",
                List.of("A boolean flag",
                        "A synchronization primitive that allows threads to wait until a condition is met",
                        "A global constant", "A thread-local variable"),
                "A synchronization primitive that allows threads to wait until a condition is met"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.MEDIUM, "What is thread local storage?",
                List.of("Shared memory", "Memory that is unique to each thread", "Global variables", "Cache memory"),
                "Memory that is unique to each thread"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.EXTREME,
                "What is the Producer-Consumer problem?",
                List.of("A supply chain issue",
                        "A classic synchronization problem where producers add items to a buffer and consumers remove them",
                        "A naming convention", "A design pattern for factories"),
                "A classic synchronization problem where producers add items to a buffer and consumers remove them"));
        q.add(new Question(null, QuestionType.CONCURRENCY, QuestionDifficulty.HARD, "What is a CyclicBarrier in Java?",
                List.of("A loop construct",
                        "A synchronization aid that allows threads to wait at a barrier point and optionally run a barrier action",
                        "A type of lock", "A thread pool"),
                "A synchronization aid that allows threads to wait at a barrier point and optionally run a barrier action"));
        return q;
    }

    private List<Question> getSoftwareDesignQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC,
                "Which principle encourages programming to an interface?",
                List.of("Encapsulation", "Polymorphism", "Abstraction", "Inheritance"), "Abstraction"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "Which design pattern decouples object creation from usage?",
                List.of("Singleton", "Factory", "Observer", "Decorator"), "Factory"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "Why is dependency injection beneficial?", List.of("Improves execution speed", "Reduces memory usage",
                        "Improves testability and flexibility", "Simplifies syntax"),
                "Improves testability and flexibility"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC,
                "In SOLID principles, what does the 'S' stand for?",
                List.of("Single Execution", "Static Binding", "Single Responsibility Principle", "System Design"),
                "Single Responsibility Principle"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the main benefit of using the Repository pattern?",
                List.of("Improves query speed", "Decouples the business logic from data access details",
                        "Replaces the database entirely", "Simplifies HTML templates"),
                "Decouples the business logic from data access details"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "Which SOLID principle suggests that classes should be open for extension but closed for modification?",
                List.of("Single Responsibility", "Open-Closed Principle", "Liskov Substitution",
                        "Interface Segregation"),
                "Open-Closed Principle"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC, "What is Encapsulation?",
                List.of("Making all variables public",
                        "Bundling data and methods that work on that data within a single unit",
                        "Creating many objects", "Using inheritance"),
                "Bundling data and methods that work on that data within a single unit"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the primary purpose of the Observer pattern?",
                List.of("To hide data",
                        "To define a one-to-many dependency so when one object changes state, all dependents are notified",
                        "To create unique objects", "To speed up algorithms"),
                "To define a one-to-many dependency so when one object changes state, all dependents are notified"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "Which pattern is used to provide a unified interface to a set of interfaces in a subsystem?",
                List.of("Adapter", "Facade", "Bridge", "Composite"), "Facade"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What does 'Composition over Inheritance' mean?",
                List.of("Write more classes",
                        "Reuse code by containing instances of other classes rather than inheriting",
                        "Always use inheritance", "Don't use classes"),
                "Reuse code by containing instances of other classes rather than inheriting"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC, "What is the DRY principle?",
                List.of("Don't Repeat Yourself", "Do Repeat Yourself", "Digital Realignment Yearly",
                        "Data Recovery Yield"),
                "Don't Repeat Yourself"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "What is the Liskov Substitution Principle?",
                List.of("Subclasses should be able to replace their base classes without affecting correctness",
                        "Every class must have an interface", "Don't use subclasses", "Use substitution for speed"),
                "Subclasses should be able to replace their base classes without affecting correctness"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is a 'Singleton' pattern?",
                List.of("A pattern for making many objects",
                        "Ensures a class has only one instance and provides a global point of access to it",
                        "A type of array", "A single-threaded application"),
                "Ensures a class has only one instance and provides a global point of access to it"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC, "What is a 'Design Pattern'?",
                List.of("A graphic design",
                        "A general repeatable solution to a commonly occurring problem in software design",
                        "A syntax rule", "A hardware specification"),
                "A general repeatable solution to a commonly occurring problem in software design"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the 'State' pattern used for?",
                List.of("Storing user data", "Allowing an object to alter its behavior when its internal state changes",
                        "Saving the app's state to disk", "Managing network states"),
                "Allowing an object to alter its behavior when its internal state changes"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "Which pattern is best for attaching additional responsibilities to an object dynamically?",
                List.of("Proxy", "Decorator", "Strategy", "Chain of Responsibility"), "Decorator"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the purpose of the 'Strategy' pattern?",
                List.of("Winning a game", "Defining a family of algorithms and making them interchangeable",
                        "Encapsulating data", "Providing a backup plan"),
                "Defining a family of algorithms and making them interchangeable"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC,
                "What does 'Loose Coupling' imply?",
                List.of("Components are highly dependent",
                        "Components have little knowledge of each other, making them easier to change",
                        "The system is slow", "The code is disorganized"),
                "Components have little knowledge of each other, making them easier to change"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "What is the 'Dependency Inversion' principle?",
                List.of("Inverting the order of methods",
                        "High-level modules should not depend on low-level modules; both should depend on abstractions",
                        "Injecting dependencies at runtime only", "Removing all dependencies"),
                "High-level modules should not depend on low-level modules; both should depend on abstractions"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM, "What is a 'Domain Model'?",
                List.of("A website template",
                        "A conceptual model of the domain that incorporates both behavior and data",
                        "A type of database", "A security layer"),
                "A conceptual model of the domain that incorporates both behavior and data"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC, "What is 'Polymorphism'?",
                List.of("Creating many files", "The ability of an object to take on many forms",
                        "Using many processors", "Writing code in multiple languages"),
                "The ability of an object to take on many forms"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the purpose of the 'Template Method' pattern?",
                List.of("To create many templates",
                        "Defining the skeleton of an algorithm and letting subclasses redefine certain steps",
                        "To format HTML", "To manage user logins"),
                "Defining the skeleton of an algorithm and letting subclasses redefine certain steps"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "What is 'Inversion of Control' (IoC)?",
                List.of("Inverting the user interface",
                        "A design principle where the control of objects or portions of a program is transferred to a container or framework",
                        "Closing the program early", "Reversing the flow of data"),
                "A design principle where the control of objects or portions of a program is transferred to a container or framework"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.BASIC, "What is 'Inheritance'?",
                List.of("Getting money from someone", "A mechanism where a new class is derived from an existing class",
                        "Copying a file", "Sharing a database"),
                "A mechanism where a new class is derived from an existing class"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the 'Command' pattern?",
                List.of("Using a terminal",
                        "Encapsulating a request as an object, thereby letting you parameterize clients with different requests",
                        "Giving orders to the CPU", "A type of script"),
                "Encapsulating a request as an object, thereby letting you parameterize clients with different requests"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.EXTREME,
                "What is Domain-Driven Design (DDD)?",
                List.of("A UI framework",
                        "An approach to software development that centers the design on the core domain and domain logic",
                        "A database design method", "A testing strategy"),
                "An approach to software development that centers the design on the core domain and domain logic"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.EXTREME,
                "What is the Hexagonal Architecture (Ports and Adapters)?",
                List.of("A physical server layout",
                        "An architectural pattern that isolates the core logic from external concerns using ports and adapters",
                        "A database schema", "A network topology"),
                "An architectural pattern that isolates the core logic from external concerns using ports and adapters"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "What is the Builder pattern used for?",
                List.of("Building servers",
                        "Constructing complex objects step by step, allowing different representations",
                        "Compiling code", "Managing dependencies"),
                "Constructing complex objects step by step, allowing different representations"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is the Adapter pattern?",
                List.of("A power converter", "A pattern that allows incompatible interfaces to work together",
                        "A database connector", "A thread wrapper"),
                "A pattern that allows incompatible interfaces to work together"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.EXTREME,
                "What is CQRS (Command Query Responsibility Segregation)?",
                List.of("A database type", "A pattern that separates read and write operations into different models",
                        "A security protocol", "A testing framework"),
                "A pattern that separates read and write operations into different models"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "What is the Prototype pattern?", List.of("Initial code version",
                        "Creating new objects by cloning an existing object", "A testing mock", "A first draft design"),
                "Creating new objects by cloning an existing object"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.MEDIUM,
                "What is cohesion in software design?",
                List.of("Gluing modules together", "The degree to which elements of a module belong together",
                        "Code duplication", "Inheritance depth"),
                "The degree to which elements of a module belong together"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.EXTREME, "What is Event Sourcing?",
                List.of("Logging events", "Storing all changes to application state as a sequence of events",
                        "Event handling in UI", "A messaging protocol"),
                "Storing all changes to application state as a sequence of events"));
        q.add(new Question(null, QuestionType.SOFTWARE_DESIGN, QuestionDifficulty.HARD,
                "What is the Chain of Responsibility pattern?",
                List.of("A management hierarchy",
                        "A pattern where a request is passed along a chain of handlers until one handles it",
                        "Error handling", "A linked list"),
                "A pattern where a request is passed along a chain of handlers until one handles it"));
        return q;
    }

    private List<Question> getDebuggingQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC,
                "What is the first step in debugging unexpected behavior?",
                List.of("Rewrite the code", "Add more features", "Reproduce the issue", "Deploy to production"),
                "Reproduce the issue"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "Why are logs useful in debugging?",
                List.of("They improve performance", "They replace tests", "They provide execution context",
                        "They reduce memory usage"),
                "They provide execution context"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD,
                "What makes concurrency bugs difficult to reproduce?",
                List.of("They depend on timing and thread scheduling", "They only occur in production",
                        "They cause compile errors", "They occur deterministically"),
                "They depend on timing and thread scheduling"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD, "What is a 'Heisenbug'?",
                List.of("A bug caused by Heisenberg uncertainty", "A bug that only happens in production",
                        "A bug that seems to disappear or change behavior when you try to study it",
                        "A deterministic logic error"),
                "A bug that seems to disappear or change behavior when you try to study it"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM,
                "What is the primary use of a performance profiler?",
                List.of("Finding syntax errors", "Automating unit tests", "Identifying bottlenecks and resource usage",
                        "Managing version control"),
                "Identifying bottlenecks and resource usage"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC,
                "What does 'Rubber Duck Debugging' involve?",
                List.of("Using a script to find bugs", "Explaining your code line-by-line to an inanimate object",
                        "Hiring a consultant", "Rewriting the entire module"),
                "Explaining your code line-by-line to an inanimate object"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is a 'Breakpoint'?",
                List.of("A point where the code crashes",
                        "A spot in the code where execution is paused to allow inspection", "A network disconnection",
                        "A syntax error"),
                "A spot in the code where execution is paused to allow inspection"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD, "What is 'Differential Debugging'?",
                List.of("Debugging math equations",
                        "Comparing a working version of the code with a non-working version to find the cause of a bug",
                        "Debugging different languages", "Using calculus to find errors"),
                "Comparing a working version of the code with a non-working version to find the cause of a bug"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC, "What is a 'Stack Trace'?", List.of(
                "A list of open files",
                "A report that shows the active stack frames at a certain point in time during the execution of a program",
                "A memory map", "A history of Git commits"),
                "A report that shows the active stack frames at a certain point in time during the execution of a program"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is a 'Watchpoint'?",
                List.of("A type of clock",
                        "A special breakpoint that stops execution when the value of a specific variable changes",
                        "A security monitor", "A point where a function is called"),
                "A special breakpoint that stops execution when the value of a specific variable changes"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD,
                "What is 'Memory Leak' debugging primarily focused on?",
                List.of("Finding syntax errors",
                        "Identifying objects that are no longer needed but are still taking up memory",
                        "Speeding up disk I/O", "Fixing network latency"),
                "Identifying objects that are no longer needed but are still taking up memory"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.EASY,
                "Which tool is commonly used to debug Java applications?",
                List.of("JDB", "Chrome DevTools", "Wireshark", "Valgrind"), "JDB"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is 'Post-mortem Debugging'?",
                List.of("Debugging a program while it's running",
                        "Debugging a program after it has crashed using a saved state (e.g., core dump)",
                        "Writing tests before code", "Fixing bugs in old software"),
                "Debugging a program after it has crashed using a saved state (e.g., core dump)"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD,
                "What is the purpose of 'Tracepoints'?",
                List.of("To stop the program", "To log information without stopping the execution of the program",
                        "To find network routes", "To measure code coverage"),
                "To log information without stopping the execution of the program"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC, "What is an 'Off-by-one Error'?",
                List.of("A type of network delay", "A logic error where a loop iterates one time too many or too few",
                        "A hardware failure", "A typo in a variable name"),
                "A logic error where a loop iterates one time too many or too few"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is 'Logging Level' used for?",
                List.of("To speed up logging", "To control the severity and volume of log messages generated",
                        "To categorize logs by user", "To encrypt log files"),
                "To control the severity and volume of log messages generated"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD,
                "What is 'Static Analysis' in the context of debugging?",
                List.of("Running the code with many inputs",
                        "Examining code without executing it to find potential bugs", "Measuring CPU usage",
                        "Analyzing network traffic"),
                "Examining code without executing it to find potential bugs"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.EASY,
                "What does it mean to 'step over' during debugging?",
                List.of("Skipping the next line", "Executing the current line of code and staying in the same function",
                        "Exiting the current function", "Entering into a function call"),
                "Executing the current line of code and staying in the same function"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is 'Regression Testing'?",
                List.of("Testing old hardware",
                        "Re-running tests to ensure that recent changes haven't broken existing functionality",
                        "Predicting future bugs", "Testing the app with many users"),
                "Re-running tests to ensure that recent changes haven't broken existing functionality"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD,
                "What is a 'Race-condition Detector'?",
                List.of("A tool that speeds up threads", "A tool used to find data races in multi-threaded programs",
                        "A type of firewall", "A fast compiler"),
                "A tool used to find data races in multi-threaded programs"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC,
                "What is a 'Heuristic' in debugging?", List.of("A rule of thumb or mental shortcut used to find bugs",
                        "A type of algorithm", "A syntax error", "A network tool"),
                "A rule of thumb or mental shortcut used to find bugs"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is 'Remote Debugging'?",
                List.of("Debugging with a remote control",
                        "Debugging a program running on a different machine than the debugger",
                        "Debugging without a screen", "Moving a bug to another file"),
                "Debugging a program running on a different machine than the debugger"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD, "What is the 'Salami Slicing' bug?",
                List.of("A very thin bug",
                        "A series of many small, barely noticeable errors that accumulate to a large error",
                        "A bug in a food app", "A fragmented file"),
                "A series of many small, barely noticeable errors that accumulate to a large error"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.BASIC,
                "What does 'Triage' mean in bug management?",
                List.of("Fixing all bugs at once", "Prioritizing bugs based on severity and impact",
                        "Deleting old bugs", "Categorizing bugs by language"),
                "Prioritizing bugs based on severity and impact"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM,
                "What is a 'Sandbox' used for in debugging?",
                List.of("Playing games", "An isolated environment for testing and debugging code safely",
                        "A type of memory segment", "A high-speed network"),
                "An isolated environment for testing and debugging code safely"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.EXTREME,
                "What is symbolic execution in debugging?",
                List.of("Using symbols instead of variable names",
                        "A technique that analyzes code by treating inputs as symbolic values to explore all execution paths",
                        "Debugging with icons", "A compression technique"),
                "A technique that analyzes code by treating inputs as symbolic values to explore all execution paths"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.EXTREME, "What is fuzzing (fuzz testing)?",
                List.of("Testing with unclear requirements",
                        "Automated testing that provides random or malformed data as input to find bugs",
                        "Manual exploratory testing", "Performance testing"),
                "Automated testing that provides random or malformed data as input to find bugs"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD, "What is a core dump?",
                List.of("Deleted memory", "A file containing the recorded state of a program's memory at crash time",
                        "A disk backup", "A log file"),
                "A file containing the recorded state of a program's memory at crash time"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM, "What is a conditional breakpoint?",
                List.of("A breakpoint that only works sometimes",
                        "A breakpoint that triggers only when a specified condition is true", "A broken condition",
                        "An if statement"),
                "A breakpoint that triggers only when a specified condition is true"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.EXTREME, "What is delta debugging?",
                List.of("Debugging Greek code",
                        "A technique that systematically narrows down failure-inducing input to a minimal subset",
                        "Debugging incremental changes", "Version control debugging"),
                "A technique that systematically narrows down failure-inducing input to a minimal subset"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD, "What is an assertion?",
                List.of("A confident statement",
                        "A statement that tests a condition and halts execution if false during debugging",
                        "A type of comment", "A variable declaration"),
                "A statement that tests a condition and halts execution if false during debugging"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.MEDIUM,
                "What is 'stepping into' during debugging?",
                List.of("Taking a break", "Entering into a function call to debug its internals", "Skipping a line",
                        "Exiting the debugger"),
                "Entering into a function call to debug its internals"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.EXTREME, "What is taint analysis?",
                List.of("Checking for dirty code",
                        "Tracking the flow of untrusted data through a program to find security vulnerabilities",
                        "Code review process", "Memory cleanup"),
                "Tracking the flow of untrusted data through a program to find security vulnerabilities"));
        q.add(new Question(null, QuestionType.DEBUGGING, QuestionDifficulty.HARD, "What is binary search debugging?",
                List.of("Debugging binary files",
                        "Systematically dividing code or commits in half to isolate the source of a bug",
                        "Using binary numbers", "Low-level debugging"),
                "Systematically dividing code or commits in half to isolate the source of a bug"));
        return q;
    }

    private List<Question> getCodeReasoningQuestions() {
        List<Question> q = new ArrayList<>();
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC,
                "What does this code print?\nint x = 1;\nfor(int i = 0; i < 3; i++) x += i;\nSystem.out.println(x);",
                List.of("3", "4", "5", "6"), "4"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What is the final value of x?\nint x = 10;\nif(x > 5) x += 2;\nelse x -= 2;",
                List.of("8", "10", "12", "14"), "12"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "Why does this code cause a StackOverflowError?\n\nint factorial(int n) {\n    if (n == 0) return n;\n    return n * factorial(n - 1);\n}",
                List.of("Incorrect base case", "Heap exhaustion", "Null pointer access", "Integer overflow"),
                "Incorrect base case"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC,
                "In Java, which operator has higher precedence: && or ||?",
                List.of("&&", "||", "They have the same precedence", "It depends on the parentheses"), "&&"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What happens when you modify a String in Java (e.g., str = str + 'a')?",
                List.of("The original object is modified", "A new String object is created", "An error is thrown",
                        "The memory location remains identical"),
                "A new String object is created"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EASY,
                "What is the output of '5' + 2 in JavaScript?", List.of("7", "52", "Error", "Undefined"), "52"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What does the 'continue' keyword do in a loop?",
                List.of("Terminates the loop", "Skips the current iteration and proceeds to the next one",
                        "Restarts the loop from the beginning", "Pauses execution"),
                "Skips the current iteration and proceeds to the next one"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC,
                "What is the result of 10 / 3 in integer division (e.g., Java)?", List.of("3.33", "3", "4", "Error"),
                "3"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "What is the result of (true || false) && !true?", List.of("true", "false", "Error", "Undefined"),
                "false"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What does 'short-circuit evaluation' mean for the && operator?",
                List.of("Both sides are always evaluated", "The right side is not evaluated if the left side is false",
                        "The left side is not evaluated if the right side is false", "The code runs faster"),
                "The right side is not evaluated if the left side is false"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EASY,
                "What is the value of 'x' after: int x = 5; x++;?", List.of("5", "6", "4", "Error"), "6"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What does it mean if a function is 'pure'?",
                List.of("It has no parameters",
                        "It always returns the same output for the same input and has no side effects",
                        "It only uses integers", "It is written in C"),
                "It always returns the same output for the same input and has no side effects"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "In C, what does '*p' represent if 'p' is a pointer?", List.of("The address of p",
                        "The value stored at the address p points to", "The size of p", "A multiplication operation"),
                "The value stored at the address p points to"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC,
                "What is a 'NullPointerException'?",
                List.of("A math error",
                        "An error that occurs when an application attempts to use null in a case where an object is required",
                        "A network error", "A type of string"),
                "An error that occurs when an application attempts to use null in a case where an object is required"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What is the purpose of a 'try-catch' block?",
                List.of("To speed up code", "To handle exceptions and prevent the program from crashing",
                        "To loop through data", "To define a class"),
                "To handle exceptions and prevent the program from crashing"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD, "What is 'Recursion'?",
                List.of("A fast loop", "A method that calls itself", "A type of sorting", "A way to join strings"),
                "A method that calls itself"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EASY,
                "What is the result of 1 << 3 (bit shift left)?", List.of("3", "8", "1", "4"), "8"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM, "What is 'Type Casting'?",
                List.of("Converting a variable from one data type to another", "Deleting a variable",
                        "Creating a new class", "Printing a variable"),
                "Converting a variable from one data type to another"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "What is a 'Closure' in programming?",
                List.of("A way to end a program",
                        "A function that remembers and can access its lexical scope even when it is executed outside its original scope",
                        "Closing a database connection", "A type of class"),
                "A function that remembers and can access its lexical scope even when it is executed outside its original scope"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC, "What is a 'Boolean'?",
                List.of("A large number", "A data type with two possible values: true or false", "A special string",
                        "A type of loop"),
                "A data type with two possible values: true or false"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What is 'Hoisting' in JavaScript?",
                List.of("Lifting heavy data", "Declarations are moved to the top of their scope during compilation",
                        "Deleting unused variables", "A type of loop"),
                "Declarations are moved to the top of their scope during compilation"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "What is 'Memorization' (Memoization)?",
                List.of("Learning by rote",
                        "An optimization technique used primarily to speed up computer programs by storing the results of expensive function calls",
                        "Managing memory", "A type of sorting"),
                "An optimization technique used primarily to speed up computer programs by storing the results of expensive function calls"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.BASIC, "What is an 'Array Index'?",
                List.of("A search engine", "A numerical value used to access an element in an array",
                        "The size of an array", "A type of data"),
                "A numerical value used to access an element in an array"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What is 'Tail Call Optimization'?",
                List.of("Optimizing the end of a file",
                        "A subroutine call performed as the final action of a procedure, which can be optimized to avoid adding a new stack frame",
                        "Cleaning up memory", "A fast return"),
                "A subroutine call performed as the final action of a procedure, which can be optimized to avoid adding a new stack frame"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "What is 'Currying' in functional programming?",
                List.of("Using spices in code",
                        "The technique of translating the evaluation of a function that takes multiple arguments into evaluating a sequence of functions, each with a single argument",
                        "A way to join arrays", "A type of sorting"),
                "The technique of translating the evaluation of a function that takes multiple arguments into evaluating a sequence of functions, each with a single argument"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EXTREME,
                "What does this code output?\nint x = 5;\nSystem.out.println(x++ + ++x);",
                List.of("10", "11", "12", "13"), "12"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EXTREME,
                "What is referential transparency?",
                List.of("See-through references",
                        "An expression that can be replaced with its value without changing the program's behavior",
                        "Variable visibility", "Pointer dereferencing"),
                "An expression that can be replaced with its value without changing the program's behavior"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD,
                "What is a higher-order function?",
                List.of("A function in a class",
                        "A function that takes other functions as arguments or returns a function",
                        "A recursive function", "A main function"),
                "A function that takes other functions as arguments or returns a function"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What is the output of: System.out.println(1 + 2 + \"3\");", List.of("123", "33", "6", "Error"), "33"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EXTREME, "What is lazy evaluation?",
                List.of("Slow code",
                        "An evaluation strategy that delays expression evaluation until its value is needed",
                        "Skipping tests", "Inefficient algorithms"),
                "An evaluation strategy that delays expression evaluation until its value is needed"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD, "What is immutability?",
                List.of("Cannot be changed", "The property of an object whose state cannot be modified after creation",
                        "Permanent storage", "Constant speed"),
                "The property of an object whose state cannot be modified after creation"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.MEDIUM,
                "What is the ternary operator in Java?",
                List.of("A three-argument function", "A shorthand if-else: condition ? valueIfTrue : valueIfFalse",
                        "A loop construct", "A bit operator"),
                "A shorthand if-else: condition ? valueIfTrue : valueIfFalse"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.EXTREME,
                "What is the difference between == and .equals() in Java?",
                List.of("No difference", "== compares references; .equals() compares content (if properly overridden)",
                        "== is faster", ".equals() only works for Strings"),
                "== compares references; .equals() compares content (if properly overridden)"));
        q.add(new Question(null, QuestionType.CODE_REASONING, QuestionDifficulty.HARD, "What is autoboxing in Java?",
                List.of("Creating boxes", "Automatic conversion between primitive types and their wrapper classes",
                        "Memory allocation", "Package importing"),
                "Automatic conversion between primitive types and their wrapper classes"));
        return q;
    }
    
}

