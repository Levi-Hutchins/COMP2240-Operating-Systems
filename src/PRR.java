
/*
 * Author: Levi Hutchins - C3386116
 * Course Code: COMP2240
 * This class implements a Preemptive Round Robin Scheduling algorithm with a twist.
 * The processes are split into high and low priority queues depending their
 * assigned priorirty. High priority processes are assigned a quantum of 4 and low 2.
 * 
 * 
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class PRR {

    // Appropriate lists as well as DISP for incrementation
    private ArrayList<Process> currentProcesses;
    private ArrayList<Process> readyQueue = new ArrayList<Process>();
    private ArrayList<Process> HPC = new ArrayList<Process>();
    private ArrayList<Process> LPC = new ArrayList<Process>();
    private ArrayList<Process> processOrder = new ArrayList<Process>();
    private ArrayList<Process> completedProcesses = new ArrayList<Process>();
    private ArrayList<Process> notArrived;
    private int DISP;

    public PRR(ArrayList<Process> currentProcesses_, int DISP_) {
        this.currentProcesses = new ArrayList<>(currentProcesses_);
        this.notArrived = new ArrayList<>(currentProcesses);
        this.DISP = DISP_;
    }

    public void sortProcesses() {
        for (Process p : currentProcesses) {
            if (p.getPriority() > 2)
                LPC.add(p);
            else
                HPC.add(p);
        }
    }

    public boolean checkProcess(Process pcs) {
        return HPC.contains(pcs);
    }

    private boolean allProcessesCompleted() {
        for (Process p : currentProcesses) {
            if (p.getSrvTime() > 0)
                return false;
        }
        return true;
    }
        

    public void runAlgorithm() {
        sortProcesses();
        int currentTime = 0;

        // long startTime = System.nanoTime();
        while (!allProcessesCompleted()) {
            // Add arriving processes to the ready queue and remove them from notArrived
            Iterator<Process> iter = notArrived.iterator();
            while (iter.hasNext()) {
                Process p = iter.next();
                if (currentTime >= p.getArrTime() && !readyQueue.contains(p)) {
                    readyQueue.add(p);
                    iter.remove();
                }
            }

            if (!readyQueue.isEmpty()) {
                currentTime += DISP; 

                Process currentProcess = readyQueue.remove(0);

                if (currentProcess.getStartTime() == 0) {
                    currentProcess.setStartTime(currentTime);
                    processOrder.add(currentProcess);
                } else {
                    Process temp = new Process(currentProcess);
                    temp.setStartTime(currentTime);
                    processOrder.add(temp);
                }



                int timeQuantum = checkProcess(currentProcess) ? 4 : 2;

                for (int q = 0; q < timeQuantum && currentProcess.getSrvTime() > 0; q++) {
                    currentProcess.decreaseServTime();
                    currentTime++;

                    // Check for newly arriving processes during execution
                    iter = notArrived.iterator();
                    while (iter.hasNext()) {
                        Process p = iter.next();
                        if (p.getArrTime() <= currentTime && !readyQueue.contains(p)) {
                            readyQueue.add(p);
                            iter.remove();
                        }
                    }
                }

                if (currentProcess.getSrvTime() == 0) {
                    currentProcess.setFinishTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getOriginalSrvTime());
                    completedProcesses.add(currentProcess);
                } else {
                    readyQueue.add(currentProcess);
                }
            } else {
                // If there are no processes in the ready queue, increment the time.
                currentTime++;
            }

        }
        // long endTime = System.nanoTime();
        // System.out.println(endTime-startTime); - Used for my report getting avg over
        // 3 runs

        algorithmToString();
    }

    public void algorithmToString() {
        System.out.println("PRR:");
        for (Process p : processOrder) System.out.println("T" + p.getStartTime() + ": " + p.getPID() + "(" + p.getPriority() + ")");
        
        System.out.println();
        System.out.println("Process  Turnaround Time  Time Waiting");
        String processFig = "";

        Collections.sort(completedProcesses, Comparator.comparingInt(process -> process.getPIDInt()));

        for (Process p : completedProcesses) {

        
            processFig += p.getPID();

            processFig += (" ".repeat(7));
            processFig += p.getTurnAroundTime();
            if ((Integer.toString(p.getTurnAroundTime()).length() == 1)) processFig += (" ".repeat(17));
            else processFig += (" ".repeat(16));
            processFig += p.getWaitTime();
            processFig += "\n";

        }
        System.out.print(processFig);

    }

    /*
     * Desc: Calculates the turnaround time for all the processes
     * @param: N/A
     * @return: String val of Avg turnaround time
     * Precondition: CompletedPrcesses is not null
     * Postcondition: Average turnaround is calculated and returned
     */
    public String getAvgTurnAroundTime() {
        double avgSum = 0;
        for (Process p : completedProcesses) avgSum += p.getTurnAroundTime();
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / completedProcesses.size());
    }

    /*
     * Desc: Calculates the Average waiting time for all the processes
     * @param: N/A
     * @return: String val of Avg wait time
     * Precondition: CompletedPrcesses is not null
     * Postcondition: Average wait time is calculated and returned
     */
    public String getAvgWaitTime() {
        double avgSum = 0;
        for (Process p : completedProcesses) avgSum += p.getWaitTime();
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / completedProcesses.size());
    }

}
