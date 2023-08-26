/*
 * Author: Levi Hutchins - C3386116
 * Course Code: COMP2240
 * This class implements a Shortest Process Next (SPN) Scheduling algorithm.
 * The next process is choosen based on the shortest service time,
 * assuming it has arrived.
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SPN {
    private ArrayList<Process> currentProcesses;
    private ArrayList<Process> completedProcesses = new ArrayList<Process>();
    private ArrayList<Process> processOrder = new ArrayList<Process>();
    private int DISP;

    public SPN(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;
    }
    
    /*
     * Desc: This function returns the index of the process next in line. SPN
     * adopts looks for the shortest process within the queue as long as it has arrived (arrTime < = currTime). 
     * If two processes have the same arrival time they are compared based on ID.
     * @param: N/A
     * @return: N/A
     * Precondition: currentProcesses must be populated  
     * Postcondition: returns index of next process in line
     */
    public int getNextProcessIndex(int currTime){
        // assume first process is shortest
        int shortestProcess = currentProcesses.get(0).getSrvTime();
        int srvIndex = 0;
    
        for (int i = 0; i < currentProcesses.size(); i++){
            // if current process service time is less the shortest process
            // set that to the new shortest process and change index value

            if((currentProcesses.get(i).getSrvTime() < shortestProcess)&& (currTime >= currentProcesses.get(i).getArrTime())){
                shortestProcess = currentProcesses.get(i).getSrvTime();
                srvIndex = i;
            }
            // If two processes have the same arrival compare based on IDs
            // process IDs are compared based on their int vals eg. (p2 < p3)
            if(currentProcesses.get(i).getSrvTime() == shortestProcess){
                if (currentProcesses.get(i).getPIDInt() < currentProcesses.get(srvIndex).getPIDInt()){
                    shortestProcess = currentProcesses.get(i).getSrvTime();
                    srvIndex = i;
                }
    
    
            }
        } return srvIndex;
        }

       



    /*
     * Desc: Implements and executes the SPN algorithm
     * @param: N/A
     * @return: N/A
     * Precondition: there must be a list of processes 
     * Postcondition: completedProcesses equals currentProcesses original size
     */
    public void runAlgorithm(){
        Process currentItem;
        int currentTime = 0;
        //long startTime = System.nanoTime();
        // loop while there are processes left        
        while(currentProcesses.size() != 0){
              
            // get the next index based on SPN algorithm     
            int nextProcessIndex = getNextProcessIndex(currentTime);
            currentTime += this.DISP;

            // Calculating and changing the correct values
            currentItem = new Process(currentProcesses.get(nextProcessIndex));
            currentItem.setWaitingTime(currentTime-currentItem.getArrTime());
            currentItem.setStartTime(currentTime);
            currentTime += currentItem.getSrvTime();
            currentItem.setFinishTime(currentTime);
            currentItem.setTurnAroundTime(currentItem.getFinishTime()-currentItem.getArrTime());

            // add completed process to ordered list and completed list
            processOrder.add(currentItem);
            completedProcesses.add(currentItem);
            currentProcesses.remove(nextProcessIndex); // remove completed process   
        }
        //long endTime = System.nanoTime();
        //System.out.println(endTime - startTime);
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
        System.out.println("SPN:");
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
    
        for(Process p: completedProcesses)avgSum += p.getTurnAroundTime();
        

        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / processOrder.size());
    }
    public String getAvgWaitTime(){
        double avgSum = 0;
    
        for(Process p: processOrder) avgSum += p.getWaitTime();
        
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(avgSum / processOrder.size());
    }
}
