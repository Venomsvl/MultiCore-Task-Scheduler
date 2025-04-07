import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/* team 22 
 nour ahmed 13004694
 lana hatem 13004852
 salma sherif 13003619
 Nouran Ashraf 13007718
 Patricia Saeed 13003457
 Nouran Mohamed 13007101
 Muna Abdulcadir 13007306
 */






public class Main {
    public static void main(String[] args) throws IOException {
        ReadyQueue readyQueue = new ReadyQueue();
        Memory memory = new Memory();

        // Reading processes from files
        List<String> process1Instructions = Files.readAllLines(Paths.get("program_1.txt"));
        List<String> process2Instructions = Files.readAllLines(Paths.get("program_2.txt"));
        List<String> process3Instructions = Files.readAllLines(Paths.get("program_3.txt"));

        // Create processes with unique memory boundaries
        Process p1 = new Process(1, process1Instructions, 0, 10);
        Process p2 = new Process(2, process2Instructions, 11, 20);
        Process p3 = new Process(3, process3Instructions, 21, 30);

        // Enqueue processes into the ReadyQueue
        readyQueue.enqueue(p1);
        readyQueue.enqueue(p2);
        readyQueue.enqueue(p3);

        System.out.println("Added Process 1 from file: src/program_1.txt");
        System.out.println("Added Process 2 from file: src/Program_2.txt");
        System.out.println("Added Process 3 from file: src/Program_3.txt");
        System.out.println("Initial Ready Queue: 3 processes\n");

        int clockCycle = 0; // Track the clock cycles

        MasterCore master = new MasterCore(readyQueue, memory);
        master.executeProcesses();

        System.out.println("\nExecution Summary:");
        System.out.println("Processes Completed: 3");
        System.out.println("Total Clock Cycles: " + clockCycle);
    }
}