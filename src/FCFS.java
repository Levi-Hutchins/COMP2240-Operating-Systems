import java.util.ArrayList;
import java.util.List;

public class FCFS{
    ArrayList<Process> currentProcesses;
    ArrayList<Process> completedProcesses;
    ArrayList<Process> processOrder = new ArrayList<Process>();
    int DISP;



    public FCFS(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;

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

    

    public void runAlgorithm(){
        int time = 0;
        Process currentItem;
        fcfs();

        for (int i = 0; i < processOrder.size(); i ++){
            if (i == 0) continue;
            System.out.println("Process ID " +processOrder.get(i).getPID());
            System.out.println("Process ArrTime " +processOrder.get(i).getArrTime());
            System.out.println("Process SrvTime "  +processOrder.get(i).getSrvTime());
            System.out.println("Process Priority " +processOrder.get(i).getPriority());
            System.out.println("---------------------" );


        }   

        // while(currentProcesses.size() < completedProcesses.size()){

        //     if(currentProcesses.size() > 0){
        //         currentItem = currentProcesses.get(getNextProcessIndex());
        //         time += this.DISP;

        //     }
        // }

    }
    public ArrayList<Process> fcfs() {
    int currentTime = 0;

    for (Process process : currentProcesses) {
        currentTime =DISP;
        if (currentTime < process.getArrTime()) {
            currentTime = process.getArrTime();
        }
        //currentTime += DISP;

        process.setArrTime(currentTime);
        currentTime += process.getSrvTime();
        ///process.finishTime = currentTime;

        processOrder.add(process);
    }

    return processOrder;
}

    

 
}