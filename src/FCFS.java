import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/*
 * Author: Levi Hutchins - C3386116
 * Course Code: COMP2240
 * This class implements a First Come First Served Scheduling algorithm.
 * The next process is choosen based on arrival time (earliest first)
 * 
 */
public class FCFS{
    // Appropriate lists as well as DISP for incrementation
    private ArrayList<Process> currentProcesses;
    private ArrayList<Process> completedProcesses = new ArrayList<Process>();
    private ArrayList<Process> processOrder = new ArrayList<Process>();
    private int DISP;

    public FCFS(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;

    }

    /*
     * Desc: this function returns the index of the process next in line. FCFS
     * adopts FIFO strategy so we sort based on arrival time. If two processes
     * have the same arrival time they are compared based on ID
     * @param: N/A
     * @return: N/A
     * Precondition: currentProcesses must be populated  
     * Postcondition: returns index of next process in line
     */
    public int getNextProcessIndex(){
    // assume first process is earliest
    int earliestArrival = currentProcesses.get(0).getArrTime();
    int arrIndex = 0;

    for (int i = 0; i < currentProcesses.size(); i++){
        // if current process arrival time is less the earliest arrival 
        // set that to the new earliest arrival time and change index value
        if(currentProcesses.get(i).getArrTime() < earliestArrival){
            earliestArrival = currentProcesses.get(i).getArrTime();
            arrIndex = i;
        }
        // If two processes have the same arrival compare based on IDs
        // process IDs are compared based on their int vals eg. (p2 < p3)
        if(currentProcesses.get(i).getArrTime() == earliestArrival){
            if (currentProcesses.get(i).getPIDInt() < currentProcesses.get(arrIndex).getPIDInt()){
                earliestArrival = currentProcesses.get(i).getArrTime();
                arrIndex = i;
            }


        }
    } return arrIndex;
    }

    
    /*
     * Desc: Implements and executes the FCFS algorithm
     * @param: N/A
     * @return: N/A
     * Precondition: there must be a list of processes 
     * Postcondition: completedProcesses equals currentProcesses original size
     */
    public void runAlgorithm(){
        Process currentItem;
        int currTime = 0;
        //long startTime = System.nanoTime();
        // loop while there are processes left        
        while(currentProcesses.size() != 0){
            //if(currentProcesses.size() > 0){
              
            // get the next index based on FCFS algorithm     
            int nextProcessIndex = getNextProcessIndex();
            currTime += this.DISP;
            // add service time to current time 
            currentItem = new Process(currentProcesses.get(nextProcessIndex));
            currentItem.setWaitingTime(currTime-currentItem.getArrTime());
            currentItem.setStartTime(currTime);
            // add process finish time to the process
            currTime += currentItem.getSrvTime();
            currentItem.setFinishTime(currTime);
            // add completed process to ordered list and completed list

            currentItem.setTurnAroundTime(currentItem.getFinishTime()-currentItem.getArrTime());
            processOrder.add(currentItem);
            completedProcesses.add(currentItem);
            currentProcesses.remove(nextProcessIndex); // remove completed process

            //}
         }
        //long endTime = System.nanoTime();
        //System.out.println(endTime - startTime); - Used for my report getting avg over 3 runs
        algorithmToString();

    }
    /*
     * Desc: This function simply presents the results in the required format
     * @param: N/A
     * @return: N/A
     * Precondition: The runAlgorithm function has successfully executed
     * Postcondition: Correct output is displayed
     */
    public void algorithmToString(){
        System.out.println("FCFS:");
        for(Process p: processOrder) System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
        
        System.out.println();
        System.out.println("Process  Turnaround Time  Time Waiting");
        String processFig = "";

        // Sorts the processOrder arraylist in ascending order bases on PIDInt
        Collections.sort(processOrder, Comparator.comparingInt(process -> process.getPIDInt()));
        
        // Generate the table in the correct format 
        for(Process p: processOrder){

            processFig += p.getPID();
            processFig += (" ".repeat(7));
            processFig += p.getTurnAroundTime();
            if((Integer.toString(p.getTurnAroundTime()).length() == 1)) processFig += (" ".repeat(17));
            else processFig += (" ".repeat(16));
            processFig += p.getWaitTime();
            processFig +="\n";
        }
        System.out.print(processFig);

    }
    public String getAvgTurnAroundTime(){
        double avgSum = 0;
    
        for(Process p: completedProcesses) avgSum += p.getTurnAroundTime();
        
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / completedProcesses.size());
    }
    public String getAvgWaitTime(){
        double avgSum = 0;
    
        for(Process p: completedProcesses) avgSum += p.getWaitTime();
        
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / completedProcesses.size());
    }
    

    

 
}