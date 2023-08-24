import java.util.ArrayList;

public class PRR {
    private ArrayList<Process> currentProcesses;
    private ArrayList<Process> readyQueue;

    private ArrayList<Process> HPC = new ArrayList<Process>();
    private ArrayList<Process> LPC = new ArrayList<Process>();

    private ArrayList<Process> processOrder = new ArrayList<Process>();
    private int DISP;



    public PRR(ArrayList<Process> currentProcesses_, int DISP_){
        this.currentProcesses = currentProcesses_;
        this.DISP = DISP_;

    }

    public void sortProcesses(){
        for(Process p: currentProcesses) if(p.getPriority() > 2) LPC.add(p);    
        else HPC.add(p);
        
    }
    public boolean checkProcess(Process pcs){
        // will return true otherwise false - in which case is in LPC
        return HPC.contains(pcs);
    }

    public void runAlgorithm(){
        sortProcesses();
        

        
    }
}
