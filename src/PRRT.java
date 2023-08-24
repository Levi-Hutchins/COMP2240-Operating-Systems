import java.util.ArrayList;
import java.util.Iterator;

public class PRRT {
    private ArrayList<Process> currentProcesses;
    private ArrayList<Process> readyQueue = new ArrayList<Process>();

    private ArrayList<Process> HPC = new ArrayList<Process>();
    private ArrayList<Process> LPC = new ArrayList<Process>();

    private ArrayList<Process> processOrder = new ArrayList<Process>();
    private int DISP;

    public PRRT(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;
    }

    public void sortProcesses(){
        for(Process p: currentProcesses) {
            if(p.getPriority() > 2) LPC.add(p);    
            else HPC.add(p);
        }
    }

    public boolean checkProcess(Process pcs){
        return HPC.contains(pcs);
    }
    
    public void test2(){
            sortProcesses();
            int currentTime = 0;
            ArrayList<Process> notArrived = new ArrayList<>(currentProcesses);
        
            while(!allProcessesCompleted()) {
        
                // Add arriving processes to the ready queue and remove them from notArrived
                Iterator<Process> iter = notArrived.iterator();
                while(iter.hasNext()) {
                    Process p = iter.next();
                    if(currentTime >= p.getArrTime() && !readyQueue.contains(p)) {
                        readyQueue.add(p);
                        iter.remove();
                    }
                }
        
                if(!readyQueue.isEmpty()) {
                    currentTime += DISP; // Add the dispatch time
                    if (currentTime == 9){
                        for(Process p: readyQueue) System.out.println(p.getPID());
                    }
                    Process currentProcess = readyQueue.remove(0);
                    
                    
                    if(currentProcess.getStartTime() == 0) {
                        currentProcess.setStartTime(currentTime);
                        processOrder.add(currentProcess);
                    }else{
                        Process temp = new Process(currentProcess);
                        temp.setStartTime(currentTime);
                        processOrder.add(temp);
                    }
                    //processOrder.add(currentProcess);

                    //currentProcess.setStartTime(currentTime);

                    // create new process to add to process order
        
                    int timeQuantum = checkProcess(currentProcess) ? 4 : 2;
        
                    for(int q = 0; q < timeQuantum && currentProcess.getSrvTime() > 0; q++) {
                        currentProcess.decreaseServTime();
                        currentTime++;
        
                        // Check for newly arriving processes during execution
                        iter = notArrived.iterator();
                        while(iter.hasNext()) {
                            Process p = iter.next();
                            if(p.getArrTime() <= currentTime && !readyQueue.contains(p)) {
                                readyQueue.add(p);
                                iter.remove();
                            }
                        }
                    }
        
                    if(currentProcess.getSrvTime() == 0) {
                        currentProcess.setFinishTime(currentTime);
                        currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getOriginalSrvTime());
                    } else {
                        readyQueue.add(currentProcess);
                    }
                } else {
                    // If there are no processes in the ready queue, increment the time.
                    currentTime++;
                }
            }
        
            

            algorithmToString();
        }

        public void summariseProcesses(){
            for(Process p : processOrder){

            }
        }

        public void algorithmToString(){
            System.out.println("PRR:");
            for(Process p: processOrder){
                
                
                System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
            }
            System.out.println();
            System.out.println("Process  Turnaround Time  Time Waiting");
            String processFig = "";
    
            for(Process p: processOrder){
    
                //System.out.println(p.getPID());
                if(p.getTurnAroundTime() == 0 || p.getWaitTime() == 0) continue;
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

        
        
        

    private boolean allProcessesCompleted() {
        for(Process p : currentProcesses) {
            if(p.getSrvTime() > 0) return false;
        }
        return true;
    }
}

