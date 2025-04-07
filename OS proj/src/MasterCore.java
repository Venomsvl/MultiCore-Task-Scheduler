import java.util.concurrent.ConcurrentLinkedQueue;

public class MasterCore {
    private final ReadyQueue readyQueue;
    private final Memory memory;

    public MasterCore(ReadyQueue readyQueue, Memory memory) {
        this.readyQueue = readyQueue;
        this.memory = memory;
    }

    public void executeProcesses() {
        int clockCycle = 0;
        ConcurrentLinkedQueue<Process> processingQueue = new ConcurrentLinkedQueue<>();

        while (!readyQueue.isEmpty() || !processingQueue.isEmpty()) {
            System.out.println("\nClock Cycle: " + clockCycle);

            // Move processes to the processing queue if available
            while (!readyQueue.isEmpty() && processingQueue.size() < 2) {
                processingQueue.add(readyQueue.dequeue());
            }

            // Execute one instruction from each core
            for (Process process : processingQueue) {
                String instruction = process.getNextInstruction();
                if (instruction != null) {
                    System.out.println("Executing Process " + process.getProcessID() + " - " + instruction);
                    SlaveCore slave = new SlaveCore(memory);
                    slave.assignProcess(process);
                    slave.start();
                    try {
                        slave.join();
                    } catch (InterruptedException e) {
                        System.err.println("Error during process execution: " + e.getMessage());
                    }
                }

                if (process.isComplete()) {
                    System.out.println("Process " + process.getProcessID() + " has completed execution.");
                } else {
                    readyQueue.enqueue(process);
                }
            }

            // Update processing queue for the next cycle
            processingQueue.clear();

            // Print memory state
            System.out.println("Memory State:");
            memory.printState();

            clockCycle++;
        }

        System.out.println("\nAll processes have completed execution.");
    }
}
