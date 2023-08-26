public class Process {
    private String PID;
    private int ArrTime;
    private int SrvTime;
    private int Priority;
    private int PIDInt;
    private int startTime;
    private int finishTime;
    private int turnAroundTime;
    private int waitTime;
    private int originalSrvTime;

    public Process(String PID, int arrTime, int srvTime, int priority, int PIDInt_ ){
        this.PID = PID;
        this.ArrTime = arrTime;
        this.SrvTime = srvTime;
        this.originalSrvTime = srvTime;
        this.Priority = priority;
        this.PIDInt = PIDInt_;  
        this.startTime = 0;
        this.finishTime = 0;
        this.turnAroundTime = 0;
        this.waitTime = 0;
    }
    // This constructor allows me to copy a existing process and making changes without
    // those changes being reflected in the copied object
    public Process(Process p){
        this.PID = p.getPID();
        this.ArrTime = p.getArrTime();
        this.SrvTime = p.getSrvTime();
        this.originalSrvTime = p.getOriginalSrvTime();
        this.Priority = p.getPriority();
        this.PIDInt = p.getPIDInt();
        this.startTime = 0;
        this.finishTime = 0;
        this.turnAroundTime = 0;
        this.waitTime = 0;
    }

    // All appropriate getters
    public int getArrTime() {return ArrTime;}
    public int getPriority() {return Priority;}
    public int getSrvTime() {return SrvTime;}
    public String getPID() {return PID;}
    public int getPIDInt(){return PIDInt;}
    public int getStartTime(){return this.startTime;}
    public int getFinishTime(){return this.finishTime;}
    public int getTurnAroundTime(){return this.turnAroundTime;}
    public int getWaitTime(){return this.waitTime;}

    // All appropriate setters
    public void setArrTime(int arrTime){ArrTime = arrTime; }
    public void setPriority(int priority_){Priority = priority_; }
    public void setSrvTime(int srvTime_){SrvTime = srvTime_; }
    public int getOriginalSrvTime(){return this.originalSrvTime; }
    public void decreaseServTime(){this.SrvTime -=1; }
    public void setPID(String PID){this.PID = PID; }
    public void setStartTime(int start_){this.startTime = start_; }
    public void setFinishTime(int finish_){this.finishTime = finish_; }
    public void setTurnAroundTime(int time_){this.turnAroundTime = time_; }
    public void setWaitingTime(int time_){this.waitTime = time_; }

}
