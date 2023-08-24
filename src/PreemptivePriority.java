import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class PreemptivePriority {

    private ArrayList<Process> processList;
    private ArrayList<Process> readyQueue = new ArrayList<Process>();
    private ArrayList<Process> processOrder = new ArrayList<Process>();
    private ArrayList<Process> notArrived;
    private int DISP;
    private int currentTime;
    private Process currentProcess;

    public PreemptivePriority(ArrayList<Process> currentProcesses) {
        this.processList = new ArrayList<>(currentProcesses);
        this.notArrived = new ArrayList<>(currentProcesses);

        this.currentTime = 0;
        this.currentProcess = null;
        this.DISP =1;
    }

    public void simulate() {
        while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {
            // Add processes that have arrived to the ready queue
            for (int i = 0; i < processList.size(); i++) {
                Process p = processList.get(i);
                if (p.getArrTime() <= currentTime) {
                    readyQueue.add(p);
                    processList.remove(i);
                    i--;
                }
            }

            // Sort the ready queue by priority
            // Collections.sort(readyQueue, new Comparator<Process>() {
            //     public int compare(Process p1, Process p2) {
            //         return Integer.compare(p1.getPriority(), p2.getPriority());
            //     }
            // });

            // Check for preemption
            if (currentProcess == null || !readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority()) {
                if (currentProcess != null) {
                    readyQueue.add(currentProcess);
                }
                currentProcess = readyQueue.remove(0);
                processOrder.add(currentProcess);
                currentProcess.setStartTime(currentTime);
            }

            if (currentProcess != null) {
                // Decrease remaining service time for the current process
                currentProcess.decreaseServTime();
                if (currentProcess.getSrvTime() == 0) {
                    currentProcess.setFinishTime(currentTime + 1);
                    currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getSrvTime());
                    currentProcess = null;
                }
            }

            currentTime++;
        }
        System.out.println("PP:");
        for(Process p: processOrder){

            System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
        }
    }


    public void simulate2() {
        while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {
            
            // Add processes that have arrived to the ready queue
            for (int i = 0; i < processList.size(); i++) {
                Process p = processList.get(i);
                if (p.getArrTime() <= currentTime) {
                    readyQueue.add(p);
                    processList.remove(i);
                    i--;
                }
            }

            // Sort the ready queue by priority
            Collections.sort(readyQueue, new Comparator<Process>() {
                public int compare(Process p1, Process p2) {
                    return Integer.compare(p1.getPriority(), p2.getPriority());
                }
            });

            // Check for preemption
            if (currentProcess == null || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
                if (currentProcess != null) {
                    readyQueue.add(currentProcess);
                }
                currentProcess = readyQueue.remove(0);
            }

            if (currentProcess != null) {
                // Decrease remaining service time for the current process
                currentProcess.decreaseServTime();
                if (currentProcess.getSrvTime() == 0) {
                    currentProcess.setFinishTime(currentTime + 1);
                    currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getSrvTime());
                    currentProcess = null;
                }
            }
            System.out.println("T" + currentTime + ": " + currentProcess.getPID() + "(" + currentProcess.getPriority() + ")");


            currentTime++;
        }
    }


    public void simulate3() { //Probably the closest one so far
        while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {
            
            // Add processes that have arrived to the ready queue
            for (int i = 0; i < processList.size(); i++) {
                Process p = processList.get(i);
                if (p.getArrTime() <= currentTime) {
                    readyQueue.add(p);
                    processList.remove(i);
                    i--;
                }
            }

            // Sort the ready queue by priority
            Collections.sort(readyQueue, new Comparator<Process>() {
                public int compare(Process p1, Process p2) {
                    return Integer.compare(p1.getPriority(), p2.getPriority());
                }
            });

            // Check for preemption
            if (currentProcess == null || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
                if (currentProcess != null) {
                    readyQueue.add(currentProcess);
                }
                currentProcess = readyQueue.remove(0);
                System.out.println("T" + currentTime + ": " + currentProcess.getPID() + "(" + currentProcess.getPriority() + ")");
            }

            if (currentProcess != null) {
                // Decrease remaining service time for the current process
                currentProcess.decreaseServTime();
                if (currentProcess.getSrvTime() == 0) {
                    currentProcess.setFinishTime(currentTime + 1);
                    currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getSrvTime());
                    currentProcess = null;
                }
            }

            currentTime++;
        }
    }
        public void simulate4() {
            while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {
                
                // Add processes that have arrived to the ready queue
                for (int i = 0; i < processList.size(); i++) {
                    Process p = processList.get(i);
                    if (p.getArrTime() <= currentTime) {
                        readyQueue.add(p);
                        processList.remove(i);
                        i--;
                    }
                }
        
                // Sort the ready queue by priority
                Collections.sort(readyQueue, new Comparator<Process>() {
                    public int compare(Process p1, Process p2) {
                        return Integer.compare(p1.getPriority(), p2.getPriority());
                    }
                });
        
                // If there's no current process or a higher-priority process is available, switch to the highest-priority process
                if (currentProcess == null || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
                    if (currentProcess != null && currentProcess.getSrvTime() > 0) {
                        // Don't preempt if the current process is not finished yet, just add it back to the queue
                        readyQueue.add(currentProcess);
                    }
                    currentProcess = readyQueue.remove(0);
                    System.out.println("T" + (currentTime + 1) + ": " + currentProcess.getPID() + "(" + currentProcess.getPriority() + ")");
                }
        
                if (currentProcess != null) {
                    // Decrease remaining service time for the current process
                    currentProcess.decreaseServTime();
                    if (currentProcess.getSrvTime() == 0) {
                        currentProcess.setFinishTime(currentTime + 1);
                        currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getSrvTime());
                        currentProcess = null; // Only set the current process to null if it's finished
                    }
                }
                currentTime++;
            }
        }

        public void simulate5() { // THis is now the closest. just need to fix times
            int currentTime = 0; // Initialize to 1
        
            while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {
                currentTime+= this.DISP;



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
        
                // Check for preemption
                if (currentProcess == null || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
                    if (currentProcess != null) {
                        readyQueue.add(currentProcess);
                    }
                    currentProcess = readyQueue.remove(0);

                    currentProcess.setStartTime(currentTime);

                    System.out.println("T" + currentProcess.getStartTime() + ": " + currentProcess.getPID() + "(" + currentProcess.getPriority() + ")");
                }
        
                if (currentProcess != null) {
                    // Decrease remaining service time for the current process
 
                    currentProcess.decreaseServTime();



                    if (currentProcess.getSrvTime() == 0) {
                        currentProcess.setFinishTime(currentTime);

                        currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getSrvTime());  // Use the original service time
                        processOrder.add(currentProcess);

                        currentProcess = null;
                    }
                    
                }
        
            }
            System.out.println("");
            for(Process p: processOrder){
                System.out.println(p.getPID()+" "+p.getStartTime());
            }

            
        }

        public void test2(){
             int currentTime = 0; // Initialize to 1
        
            while (!processList.isEmpty() || !readyQueue.isEmpty() || currentProcess != null) {
                currentTime+= this.DISP;



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
        
                // Check for preemption
                if (currentProcess == null || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
                    if (currentProcess != null) {
                        readyQueue.add(currentProcess);
                        currentProcess = null;
                        currentTime += this.DISP;
                    }
                    currentProcess = readyQueue.remove(0);

                    currentProcess.setStartTime(currentTime);

                    System.out.println("T" + currentProcess.getStartTime() + ": " + currentProcess.getPID() + "(" + currentProcess.getPriority() + ")");
                }
        
                if (currentProcess != null) {
                    // Decrease remaining service time for the current process
 
                    currentProcess.decreaseServTime();
                    currentTime += DISP;


                    if (currentProcess.getSrvTime() == 0) {
                        currentProcess.setFinishTime(currentTime);

                        currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getSrvTime());  // Use the original service time
                        processOrder.add(currentProcess);

                        currentProcess = null;
                    }
                    
                }
                
        
            }
            System.out.println("");
            for(Process p: processOrder){
                System.out.println(p.getPID()+" "+p.getStartTime());
            } 

        }



        public void myTest(){
            int currentTime = 0; // Initialize to 0

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

        // Check for preemption
        if (currentProcess == null || (!readyQueue.isEmpty() && readyQueue.get(0).getPriority() < currentProcess.getPriority())) {
            if (currentProcess != null) {
                readyQueue.add(currentProcess);
            }

            currentProcess = readyQueue.remove(0);

            currentTime += this.DISP; // Move this line here
            if(currentProcess.getStartTime() == 0) {
                currentProcess.setStartTime(currentTime);
                processOrder.add(currentProcess);
            }else{
                Process temp = new Process(currentProcess);
                temp.setStartTime(currentTime);
                processOrder.add(temp);
            }

        }

        if (currentProcess != null) {
            // Decrease remaining service time for the current process
            currentProcess.decreaseServTime();

            currentTime++;  // Increment time after processing

            if (currentProcess.getSrvTime() == 0) {
                currentProcess.setFinishTime(currentTime);
                currentProcess.setTurnAroundTime(currentProcess.getFinishTime() - currentProcess.getArrTime());
                currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getOriginalSrvTime()); // Assuming there's a method getOriginalSrvTime()
                currentProcess = null;
            }
        } else {
            // If no process to run, just increase the time
            currentTime++;
        }
    }
    algorithmToString();
    
    }

    public void algorithmToString(){
        System.out.println("PP:");
        for(Process p: processOrder){

            System.out.println("T"+p.getStartTime()+ ": "+p.getPID()+"("+p.getPriority()+")");
        }

        // Collections.sort(processOrder, new Comparator<Process>() {
        //     public int compare(Process p1, Process p2) {
        //         return Integer.compare(p1.getPIDInt(), p2.getPIDInt());
        //     }
        // });
        Set<Process> set = new HashSet<Process>(processOrder);
        processOrder.clear();
        processOrder.addAll(set);
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

    }}
    

