import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PP{
    ArrayList<Process> currentProcesses;
    ArrayList<Process> completedProcesses = new ArrayList<Process>();
    ArrayList<Process> pausedQueue = new ArrayList<Process>();

    ArrayList<Process> processOrder = new ArrayList<Process>();
    int DISP;



    public PP(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;
    

    }
    public int getNextPausedProcessIndex(int currTime){
        if(pausedQueue.size() == 0)return -1;
        int highestPriority = pausedQueue.get(0).getPriority();
        int priorIndex = 0;
    
        for (int i = 0; i < pausedQueue.size(); i++){


            if((pausedQueue.get(i).getPriority() < highestPriority)&& (currTime >= pausedQueue.get(i).getArrTime())){
                highestPriority = pausedQueue.get(i).getPriority();
                priorIndex = i;
            }
            // If two processes have the same arrival compare based on IDs
            // process IDs are compared based on their int vals eg. (p2 < p3)
            if(pausedQueue.get(i).getPriority() == highestPriority){
                if (pausedQueue.get(i).getPIDInt() < pausedQueue.get(priorIndex).getPIDInt()){
                    highestPriority = pausedQueue.get(i).getPriority();
                    priorIndex = i;
                }
    
    
            }
        }
        return priorIndex;
        }
     
    public int getNextProcessIndex(int currTime){
        int highestPriority = currentProcesses.get(0).getPriority();
        int priorIndex = 0;
    
        for (int i = 0; i < currentProcesses.size(); i++){


            if((currentProcesses.get(i).getPriority() < highestPriority)&& (currTime >= currentProcesses.get(i).getArrTime())){
                highestPriority = currentProcesses.get(i).getPriority();
                priorIndex = i;
            }
            // If two processes have the same arrival compare based on IDs
            // process IDs are compared based on their int vals eg. (p2 < p3)
            if(currentProcesses.get(i).getPriority() == highestPriority){
                if (currentProcesses.get(i).getPIDInt() < currentProcesses.get(priorIndex).getPIDInt()){
                    highestPriority = currentProcesses.get(i).getPriority();
                    priorIndex = i;
                }
    
    
            }
        }
        return priorIndex;
        }
   
    public void runAlgorithm(){
        Process currentItem;
        Process nextItem; 
        int currTime = 0;
        Process prevItem = currentProcesses.get(0);
        Process pausedProcess;

        while(currentProcesses.size() != 0){
            currTime += this.DISP;
            int currIndex = getNextProcessIndex(currTime);
            currentItem = currentProcesses.get(currIndex);
            nextItem = currentProcesses.get(getNextProcessIndex(currTime+1));

            while(currentItem.getPriority() < nextItem.getPriority()){

                currentItem.decreaseServTime();


                currTime += this.DISP;
            }
            processOrder.add(currentItem);



            // int pausedIndex = getNextPausedProcessIndex(currTime);
            // currentItem = currentProcesses.get(currIndex);

            // if(pausedIndex != -1){
            //     pausedProcess = pausedQueue.get(pausedIndex);
            //     if(pausedProcess.getPriority() < currentItem.getPriority()){
            //         currentItem = pausedProcess;
            //         pausedQueue.remove(pausedProcess);
            //     }

            // }

            



            //int pausedIndex = getNextPausedProcessIndex(currTime);
            currentItem.decreaseServTime();;
            processOrder.add(currentItem);

            if(currentItem != prevItem && prevItem.getSrvTime() != 0){
                pausedQueue.add(prevItem);
            }
            


            prevItem = currentItem;
            currentProcesses.remove(currentItem);


            




            // currentItem.setWaitingTime(currTime-currentItem.getArrTime());
            // currentItem.editSrvTime(currTime);

            
            
            
            
            
            // currentItem.setStartTime(currTime);
            // currTime += currentItem.getSrvTime();
            // currentItem.setFinishTime(currTime);

            // currentItem.setTurnAroundTime(currentItem.getFinishTime()-currentItem.getArrTime());
            // processOrder.add(currentItem);
            // completedProcesses.add(currentItem);
            // currentProcesses.remove(nextProcessIndex); 

            
         }
         for(Process p:pausedQueue){
            System.out.println(p.getPID()+" "+p.getSrvTime());
         }
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
    
        System.out.println("PP:");
        for(Process p: processOrder){

            System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
        }
        System.out.println();
        System.out.println("Process  Turnaround Time  Time Waiting");
        String processFig = "";
        Comparator<Process> processComparator = Comparator.comparingInt(process -> process.getPIDInt());
        Collections.sort(processOrder, processComparator);
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