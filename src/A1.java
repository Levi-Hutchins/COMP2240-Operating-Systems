import java.io.ObjectInputFilter.Config;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class A1 {
    public static void main(String[] args) {
        if (args.length != 1){
            throw new IllegalArgumentException("Please Enter Data File");
        }
        A1 test = new A1();
        //test.readFileData(args[0]);
        int DISP = test.getDISP(args[0]);


        test.exe(test.getProcessesFromData(args[0]), DISP);

    }

    public void exe(ArrayList<Process> processes, int DISP){

        FCFS fcfs = new FCFS(processes, DISP);
        fcfs.runAlgorithm(); //- Done Tests Passed
        //SPN spn = new SPN(processes, DISP);
        //spn.runAlgorithm(); // - Done Tests Passed
        PP pp = new PP(processes, DISP);
        //pp.runAlgorithm();
        //PreemptivePriority pptest = new PreemptivePriority(processes);
        //pptest.myTest();
        //PRR prr = new PRR(processes, DISP);
        //prr.runAlgorithm();

  
        // for(int i = 0; i < processes.size(); i++){
        //     if(i == 0 ) continue;
        //     System.out.println("Process "+ i +" "+processes.get(i).getPIDInt());
        // }
    }
    public int getDISP(String file){
        int DISP = 0;
        try{
            Scanner reader = new Scanner(new File(file));
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                if(line.contains("DISP")) DISP = Integer.parseInt(line.substring(6));
            }
        }catch(FileNotFoundException e){System.out.println("An Error Occured");}
        return DISP;

    }

    public ArrayList<Process> getProcessesFromData(String file){
        ArrayList<Process> processes = new ArrayList<>();
        
        int DISP = 0;

        String pid = "";
        int arr_time = 0;
        int srv_time = 0;
        int priority = 0;
        int pidInt = 0;

        int endCount = 0;
    
        try{ 
            Scanner reader = new Scanner(new File(file));
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                if(line.contains("DISP")) DISP = Integer.parseInt(line.substring(6));

                if(line.contains("PID")){
                    pid = line.substring(5);
                    pidInt = Integer.parseInt(line.substring(6));
                }

                
                if(line.contains("ArrTime")) arr_time = Integer.parseInt(line.substring(9));
                
                if(line.contains("SrvTime")) srv_time = Integer.parseInt(line.substring(9));
                
                if(line.contains("Priority")) priority = Integer.parseInt(line.substring(10));
                
                if(line.contains("END") && endCount > 0) processes.add((new Process(pid, arr_time, srv_time, priority, pidInt)));
                
                // end of process data - create new process and add to arraylist
                if(line.contains("END")){
                    endCount++;
                }
                     
            }

        }
        catch(FileNotFoundException e){ System.out.println("An Error Occured");}
        

        // for(Process p: processes ){
        //     System.out.println(p.getPID());
        // }

        return processes;
    }
  
    
}
