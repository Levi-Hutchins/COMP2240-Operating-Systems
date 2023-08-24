import java.util.ArrayList;
import java.util.List;

public class FCFS{
    ArrayList<Process> currentProcesses;
    ArrayList<Process> completedProcesses = new ArrayList<Process>();
    ArrayList<Process> pausedProcesses = new ArrayList<Process>();

    ArrayList<Process> originalProcesses = new ArrayList<Process>();

    ArrayList<Process> processOrder = new ArrayList<Process>();
    int DISP;



    public FCFS(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.originalProcesses = currentProcesses_;
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
    }
    // return index of next process to be loaded
    return arrIndex;
    }

    
    /*
     * Desc: executes the FCFS algorithm and calls the toString function for output
     * @param: N/A
     * @return: N/A
     * Precondition: there must be a list of processes 
     * Postcondition: completedProcesses equals currentProcesses original size
     */
    public void runAlgorithm(){
        Process currentItem;
        int currTime = 0;
        // loop while there are processes left        
        while(currentProcesses.size() != 0){
            //if(currentProcesses.size() > 0){
              
            // get the next index based on FCFS algorithm     
            int nextProcessIndex = getNextProcessIndex();
            currTime += this.DISP;
            // add service time to current time 
            currentItem = currentProcesses.get(nextProcessIndex);
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
        System.out.println("Done");
        algorithmToString();

    }
    /*
     * Desc: 
     * @param: 
     * @return: 
     * Precondition:  
     * Postcondition: 
     */
    public void algorithmToString(){
        System.out.println("FCFS:");
        for(Process p: processOrder){

            System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
        }
        System.out.println();
        System.out.println("Process  Turnaround Time  Time Waiting");
        String processFig = "";

        for(Process p: processOrder){

            //System.out.println(p.getPID());
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
    

    

 
}