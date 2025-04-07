import java.util.LinkedList;
import java.util.Queue;

public class ReadyQueue {
    private final Queue<Process> queue;

    public ReadyQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void enqueue(Process process) {
        queue.add(process);
    }

    public synchronized Process dequeue() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void printQueue() {
        System.out.println("Ready Queue: ");
        for (Process process : queue) {
            System.out.print("P" + process.getProcessID() + " ");
        }
        System.out.println();
    }
}