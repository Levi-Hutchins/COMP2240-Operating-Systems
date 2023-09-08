/*
 * Author: Levi Hutchins - C3386116
 * Course Code: COMP2240
 * This class implements a Preemptive Priority Scheduling algorithm.
 * The next process is choosen based priority. Higher priority processes are
 * preempted.
 * 
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PP {

    // Appropriate lists as well as DISP for incrementation
    private ArrayList<Process> processList;
    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> completedProcesses = new ArrayList<>();
    private ArrayList<Process> processOrder = new ArrayList<>();
    private int DISP;

    public PP(ArrayList<Process> currentProcesses_, int DISP_) {
        this.processList = new ArrayList<>(currentProcesses_);
        this.DISP = DISP_;
    }
    /*
     * Desc: Implements and executes the PP algorithm
     * @param: N/A
     * @return: N/A
     * Precondition: processList is not empty 
     * Postcondition: completedProcesses equals currentProcesses original size
     */
    public void runAlgorithm() {
        int currentTime = 0; 
        Process currentProcess = null;
        //long startTime = System.nanoTime();
        // Contniue while there are processes in originl list or empty list or if no process
        while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {

            // Add processes that have arrived to the ready queue
            for (int i = 0; i < processList.size(); i++) {
                Process p = processList.get(i);
                if (p.getArrTime() <= currentTime) {
                    readyQueue.add(p);
                    processList.remove(p);
                    i--;
                }
            }

            // Sort the ready queue by priority
            Collections.sort(readyQueue, new Comparator<Process>() {
                public int compare(Process p1, Process p2) {
                    return Integer.compare(p1.getPriority(), p2.getPriority());
                }
            });

            // Check for preemption and add to the ready queue
            if (currentProcess == null
                    || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
                if (currentProcess != null) {
                    readyQueue.add(currentProcess);
                }
                // Get the process at the front of the queue
                currentProcess = new Process(readyQueue.remove(0));

                currentTime += this.DISP; 
                // If it has notbeen started set first start time
                if (currentProcess.getStartTime() == 0) {
                    currentProcess.setStartTime(currentTime);
                    processOrder.add(currentProcess);
                } else {
                    // If it aready has start time make a copy and set new start time
                    Process temp = new Process(currentProcess);
                    temp.setStartTime(currentTime);
                    processOrder.add(temp);
                }

            }

            if (currentProcess != null) {
                // Decrease remaining service time for the current process
                currentProcess.decreaseServTime();
                currentTime++; 
                if (currentProcess.getSrvTime() == 0) { // Perform necessary calculations
                    currentProcess.setFinishTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getOriginalSrvTime()); 
                    completedProcesses.add(currentProcess);
                    currentProcess = null;
                }
            } 
        }
        //long endTime = System.nanoTime(); 
        //System.out.println((endTime-startTime));  - Used for my report getting avg over 3 runs
        algorithmToString();

    }
    /*
    * Desc: This function simply presents the results in the required format
    * @param: N/A
    * @return: N/A
    * Precondition: The runAlgorithm function has successfully executed
    * Postcondition: Correct output is displayed
    */
    public void algorithmToString() {
        System.out.println("PP:");
        for (Process p : processOrder)System.out.println("T" + p.getStartTime() + ": " + p.getPID() + "(" + p.getPriority() + ")");
        
        // Sorts the processOrder arraylist in ascending order based on PIDInt
        Collections.sort(completedProcesses, Comparator.comparingInt(process -> process.getPIDInt()));
        System.out.println();
        System.out.println("Process  Turnaround Time  Waiting Time");
        String processFig = "";

        // Generate the table in the correct format 
        for (Process p : completedProcesses) {

            processFig += p.getPID();
            processFig += (" ".repeat(7));
            processFig += p.getTurnAroundTime();
            if ((Integer.toString(p.getTurnAroundTime()).length() == 1))processFig += (" ".repeat(17));
            else processFig += (" ".repeat(16));
            processFig += p.getWaitTime();
            processFig += "\n";

        } System.out.print(processFig);

    }
    /*
     * Desc: Calculates the turnaround time for all the processes
     * @param: N/A
     * @return: String val of Avg turnaround time
     * Precondition: CompletedPrcesses is not null
     * Postcondition: Average turnaround is calculated and returned
     */
    public String getAvgTurnAroundTime(){
        double avgSum = 0;
        for(Process p: completedProcesses) avgSum += p.getTurnAroundTime();
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / completedProcesses.size());
    }

    /*
     * Desc: Calculates the turnaround time for all the processes
     * @param: N/A
     * @return: String val of Avg turnaround time
     * Precondition: CompletedPrcesses is not null
     * Postcondition: Average turnaround is calculated and returned
     */
    public String getAvgWaitTime(){
        double avgSum = 0;
        for(Process p: completedProcesses) avgSum += p.getWaitTime();
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / completedProcesses.size());
    }
}
