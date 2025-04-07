
public class Scheduler {
    private final ReadyQueue readyQueue;
    private final Memory memory;

    public Scheduler(ReadyQueue readyQueue, Memory memory) {
        this.readyQueue = readyQueue;
        this.memory = memory;
    }

    public void schedule() {
        roundRobin(2); // Directly use Round Robin with a quantum of 2
    }

    private void roundRobin(int quantum) {
        int cycle = 0;
        while (!readyQueue.isEmpty()) {
            System.out.println("Cycle " + (++cycle) + ":");
            readyQueue.printQueue();

            boolean progressMade = false;  // Flag to check if any process is re-enqueued
            for (int i = 0; i < 2 && !readyQueue.isEmpty(); i++) {  // Limit to 2 processes per cycle, or adjust as needed
                Process process = readyQueue.dequeue();
                if (process != null) {
                    // Create a new SlaveCore for each process
                    SlaveCore slave = new SlaveCore(memory);
                    slave.assignProcess(process);
                    slave.start();  // Start the slave core thread
                    try {
                        slave.join(quantum * 1000);  // Simulate quantum time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!process.isComplete()) {
                        readyQueue.enqueue(process);  // Re-enqueue if not complete
                        progressMade = true;
                    }
                }
            }

            if (!progressMade) {
                // If no process was re-enqueued, break out of the loop
                break;
            }

            memory.printState();
        }
    }
}
