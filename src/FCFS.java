import java.util.ArrayList;
import java.util.List;

public class FCFS{
    ArrayList<Process> currentProcesses;
    ArrayList<Process> completedProcesses = new ArrayList<Process>();
    ArrayList<Process> processOrder = new ArrayList<Process>();
    int DISP;



    public FCFS(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;

    }

    public int getNextProcessIndex(){

    int earliestArrival = currentProcesses.get(0).getArrTime();
    int arrIndex = 0;

    for (int i = 0; i < currentProcesses.size(); i++){
        if(currentProcesses.get(i).getArrTime() < earliestArrival){
            earliestArrival = currentProcesses.get(i).getArrTime();
            arrIndex = i;
        }
        if(currentProcesses.get(i).getArrTime() == earliestArrival){
            if (currentProcesses.get(i).getPIDInt() < currentProcesses.get(arrIndex).getPIDInt()){
                earliestArrival = currentProcesses.get(i).getArrTime();
                arrIndex = i;
            }


        }
    }
    return arrIndex;
    }

    

    public void runAlgorithm(){
        Process currentItem;
        int currTime = 0;
        System.out.println(currentProcesses.size());
        
        while(currentProcesses.size() != 0){
            if(currentProcesses.size() > 0){
                
                int nextProcessIndex = getNextProcessIndex();
                System.out.println(nextProcessIndex);
                currTime += this.DISP;
                currentItem = currentProcesses.get(nextProcessIndex);
                currentItem.setStartTime(currTime);

                currTime += currentItem.getSrvTime();
                currentItem.setFinishTime(currTime);

                processOrder.add(currentItem);
                completedProcesses.add(currentItem);
                currentProcesses.remove(nextProcessIndex);

            }
         }
        System.out.println("Done");
        algorithmToString();

    }
    public void algorithmToString(){
        System.out.println("FCFS");
        for(Process p: processOrder){

            System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
        }
    }
    

    

 
}