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
        try{ 
            Scanner reader = new Scanner( new File(file));
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                if(line.equals("END")){
                    System.out.println("Here");
                }
            }

        }
        catch(FileNotFoundException e){ System.out.println("An Error Occured");}


    }
    
}
