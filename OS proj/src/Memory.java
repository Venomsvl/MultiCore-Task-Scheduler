import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;



public class Memory {
    private final Map<String, Integer> memory;
    private final ReentrantLock lock;

    public Memory() {
        memory = new HashMap<>();
        lock = new ReentrantLock();
    }

    public synchronized void write(String variable, int value) {
        memory.put(variable, value);
        System.out.println("Writing " + value + " to memory variable " + variable);
    }

    public synchronized int read(String variable) {
        return memory.getOrDefault(variable, 0);
    }


    public synchronized void printState() {
        System.out.println("Memory State: " + memory);
    }
}

