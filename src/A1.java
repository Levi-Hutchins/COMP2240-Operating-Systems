import java.io.ObjectInputFilter.Config;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class A1 {
    public static void main(String[] args) {
        if (args.length != 1){
            throw new IllegalArgumentException("Please Enter Data File");
        }
        System.out.println(args[0]);
        A1 test = new A1();
        test.readFileData(args[0]);
    }

    public void exe(String[] ags){
  
        return;
    }
    public void readFileData(String file){
        int DISP = 0;

        String pid = "";
        int arr_time = 0;
        int srv_time = 0;
        int priority = 0;
    

        try{ 
            Scanner reader = new Scanner( new File(file));
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                if(line.contains("DISP")){
                    System.out.println(line);
                    DISP = Integer.parseInt(line.substring(6));
                }
                if(line.contains("PID")){
                    pid = line.substring(5);
                }
                if(line.contains("ArrTime")){
                    arr_time = Integer.parseInt(line.substring(9));
                }
                if(line.contains("SrvTime")){
                    srv_time = Integer.parseInt(line.substring(9));
                }
                if(line.contains("Priority")){
                    priority = Integer.parseInt(line.substring(10));
                }
                if(line.contains("END")){
                    System.out.println(pid);
                    System.out.println(arr_time);
                    System.out.println(srv_time);
                    System.out.println(priority);

                }
            }

        }
        catch(FileNotFoundException e){ System.out.println("An Error Occured");}

    }
    
}
