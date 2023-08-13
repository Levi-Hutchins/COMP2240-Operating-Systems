import java.util.ArrayList;

public class FCFS{
    ArrayList<Process> currentProcesses;
    ArrayList<Process> completedProcesses;



    public FCFS(ArrayList<Process> currentProcesses_){
        this.currentProcesses = currentProcesses_;

    }

    public int getNextProcessIndex(){

        int earliestArrival = this.currentProcesses.get(0).getArrTime();
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
}