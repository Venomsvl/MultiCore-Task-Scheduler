# Multi-Core Execution System – Operating Systems Project

This Java project simulates a multi-core system using a master-slave architecture. One master core schedules tasks. Two slave cores execute tasks in parallel. Each program is a process with instructions like variable assignments, arithmetic operations, and print commands. Instructions execute in a single clock cycle.

## Features

- Master-slave CPU architecture  
- Shared memory with synchronization  
- Ready queue for process scheduling  
- PCB for each process  
- Round Robin or SJF scheduling  
- Real-time execution monitoring  

## Components

- **Ready Queue**: Manages waiting processes  
- **Memory**: Shared and synchronized  
- **PCB**: Stores PID, PC, and memory bounds  
- **Cores**: One master, two slave cores  

## Scheduling Options

- **Round Robin** (quantum = 2)  
- **Shortest Job First (SJF)**  

## Supported Instructions

- Variable assignment  
- Arithmetic operations (+, -, *, /)  
- Print output (thread-safe)  

## Outputs per Clock Cycle

- Ready queue contents  
- Process on each slave core  
- Memory state (variables and values)
  
## Project Structure

- `src/`  
  - `Main.java`  
  - `Core.java`  
  - `MasterCore.java`  
  - `SlaveCore.java`  
  - `Memory.java`  
  - `PCB.java`  
  - `Scheduler.java`  
  - `Parser.java`  

## Implementation Highlights

- Uses `extends Thread` for multi-threading  
- Each core is a thread  
- Tracks execution per clock cycle  
- Logs process status and memory state  
