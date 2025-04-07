// Updated SlaveCore.java with refined instruction parsing and output clarity
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class SlaveCore extends Thread {
    private Process process;
    private final Memory memory;
    private static final Semaphore inputSemaphore = new Semaphore(1); // Ensures input is taken one at a time

    public SlaveCore(Memory memory) {
        this.memory = memory;
    }

    public void assignProcess(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        if (process != null) {
            while (!process.isComplete()) {
                synchronized (this) { // Ensure thread-safe execution
                    String instruction = process.getNextInstruction();
                    if (instruction != null) {
                        executeInstruction(instruction);
                    } else {
                        break; // Exit if no instruction left
                    }
                }
            }
        }
    }

    // Method to execute the instruction
    private void executeInstruction(String instruction) {
        if (instruction.startsWith("print")) {
            handlePrint(instruction);
        } else if (instruction.startsWith("assign")) {
            handleAssign(instruction);
        } else if (instruction.contains("add") || instruction.contains("subtract") || instruction.contains("multiply") || instruction.contains("divide")) {
            handleArithmetic(instruction);
        } else {
            System.out.println("Unknown instruction: " + instruction);
        }
    }

    // Method to handle variable assignment
    private void handleAssign(String instruction) {
        String[] parts = instruction.split(" ");
        if (parts.length == 3 && parts[2].equals("input")) {
            String variable = parts[1];
            int value = readInput(variable);
            memory.write(variable, value);
        } else if (parts.length == 5) {
            // Redirect to handleArithmetic for arithmetic instructions
            handleArithmetic(instruction);
        } else {
            System.out.println("Invalid assign instruction: " + instruction);
        }
    }
    

    // Method to handle arithmetic operations
    private void handleArithmetic(String instruction) {
        String[] parts = instruction.split(" ");
        if (parts.length != 5) {
            System.out.println("Invalid arithmetic instruction: " + instruction);
            return;
        }

        String operation = parts[2];
        String variable = parts[1];
        String operand1 = parts[3];
        String operand2 = parts[4];

        Integer op1Value = memory.read(operand1);
        Integer op2Value = memory.read(operand2);

        if (op1Value == null || op2Value == null) {
            System.out.println("Operands not found in memory for: " + instruction);
            return;
        }

        int result = 0;
        switch (operation) {
            case "add":
                result = op1Value + op2Value;
                break;
            case "subtract":
                result = op1Value - op2Value;
                break;
            case "multiply":
                result = op1Value * op2Value;
                break;
            case "divide":
                if (op2Value == 0) {
                    System.out.println("Error: Division by zero in process " + process.getProcessID());
                    return;
                }
                result = op1Value / op2Value;
                break;
            default:
                System.out.println("Unknown arithmetic operation: " + operation);
                return;
        }

        memory.write(variable, result);
        System.out.println("Writing " + result + " to memory variable " + variable);
    }

    // Method to handle print instructions
    private void handlePrint(String instruction) {
        String variable = instruction.split(" ")[1];
        Integer value = memory.read(variable);
        System.out.println("Process " + process.getProcessID() + ": " + variable + " = " + (value != null ? value : "null"));
    }

    // Method to read user input safely
    private int readInput(String variable) {
        int value = 0;
        try {
            inputSemaphore.acquire(); // Acquire the semaphore to prevent simultaneous input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter value for " + variable + ": ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next(); // Consume invalid input
            }
            value = scanner.nextInt();
        } catch (InterruptedException e) {
            System.out.println("Error acquiring semaphore for input: " + e.getMessage());
        } finally {
            inputSemaphore.release(); // Release the semaphore
        }
        return value;
    }
}