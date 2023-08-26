/*
 * Author: Levi Hutchins - C3386116
 * Course Code: COMP2240
 * This class is the main file for responsible executing all of the 
 * algorithms and presenting the results in the required format.
 */
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class A1 {
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Please Enter Data File");
        
        A1 program = new A1();
        int DISP = program.getDISP(args[0]); // Get disp value pass args[0] - file namr
        // Reading all processes from datafile and pass it to the function calling all the algorithms
        program.execute(program.getProcessesFromData(args[0]), DISP); 

    }

    /*
     * Desc: This method creates and runs all the specified algorithms with the process list
     * @param: list of processes, dispatcher value
     * @return:
     * Precondition: processList is not empty and dispatch != 0 
     * Postcondition: all algorithms run successfully and output is presented
     */
    public void execute(ArrayList<Process> processes, int DISP){
        FCFS fcfs = new FCFS(new ArrayList<>(processes), DISP);
        fcfs.runAlgorithm(); //- Done Tests Passed
        System.out.println("");

        SPN spn = new SPN(new ArrayList<>(processes), DISP);
        spn.runAlgorithm(); // - Done Tests Passed
        System.out.println("");

        PP pp = new PP(new ArrayList<>(processes), DISP);
        pp.runAlgorithm(); // - Done Tests Passed
        System.out.println("");

        PRR prr = new PRR(processes, DISP);
        prr.runAlgorithm(); // - Done Tests Passed
        System.out.println("");

        System.out.println("Summary");
        System.out.println("Algorithm\tAverage Turnaround Time \tAverage Waiting Time");
        System.out.println("FCFS     \t"+fcfs.getAvgTurnAroundTime()+("\t".repeat(4))+fcfs.getAvgWaitTime());
        System.out.println("SPN     \t"+spn.getAvgTurnAroundTime()+("\t".repeat(4))+spn.getAvgWaitTime());
        System.out.println("PP      \t"+pp.getAvgTurnAroundTime()+("\t".repeat(4))+pp.getAvgWaitTime());
        System.out.println("PRR     \t"+prr.getAvgTurnAroundTime()+("\t".repeat(4))+prr.getAvgWaitTime());
    }
    /*
     * Desc: Read and assigined dispatcher value from the datafiles
     * @param: file name
     * @return: N/A
     * Precondition: file exists 
     * Postcondition: dispatcher value is found and returned
     */
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

    /*
     * Desc: Reads data from datafile and creates a single processes object.
     *       Once the string "END" is seen add the process to the list of processes
     * @param: file name
     * @return: list of processes
     * Precondition: files exists 
     * Postcondition: list of processes is created and returned 
     */
    public ArrayList<Process> getProcessesFromData(String file){
        ArrayList<Process> processes = new ArrayList<>();
        int DISP = 0;
        String pid = "";
        int arr_time= 0;
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
                    pidInt = Integer.parseInt(line.substring(6)); // getting the int value from the substring
                }
                // Find all the process variables from datafile and create object 
                if(line.contains("ArrTime")) arr_time = Integer.parseInt(line.substring(9));
                if(line.contains("SrvTime")) srv_time = Integer.parseInt(line.substring(9));
                if(line.contains("Priority")) priority = Integer.parseInt(line.substring(10));
                if(line.contains("END") && endCount > 0) processes.add((new Process(pid, arr_time, srv_time, priority, pidInt)));
                // end of process data - create new process and add to arraylist
                if(line.contains("END")) endCount++;
            }

        }
        catch(FileNotFoundException e){ System.out.println("An Error Occured");} // Catch file not found error
        return processes;
    }
    
  
    
}
