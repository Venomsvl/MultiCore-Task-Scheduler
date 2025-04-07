import java.util.List;

public class Process {
    private final int processID;
    private int programCounter;
    private final List<String> instructions;
    private final int memoryStart;
    private final int memoryEnd;

    public Process(int processID, List<String> instructions, int memoryStart, int memoryEnd) {
        this.processID = processID;
        this.instructions = instructions;
        this.memoryStart = memoryStart;
        this.memoryEnd = memoryEnd;
        this.programCounter = 0; // Start at the first instruction
    }

    public int getProcessID() {
        return processID;
    }

    public String getNextInstruction() {
        if (programCounter < instructions.size()) {
            return instructions.get(programCounter++);
        }
        return null; // Process has no more instructions
    }

    public boolean isComplete() {
        return programCounter >= instructions.size();
    }

    public int getMemoryStart() {
        return memoryStart;
    }

    public int getMemoryEnd() {
        return memoryEnd;
    }
}