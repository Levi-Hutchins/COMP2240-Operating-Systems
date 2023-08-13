public class Process {
    private String PID;
    private int ArrTime;
    private int SrvTime;
    private int Priority;
    private int PIDInt;
    private int startTime;
    private int finishTime;

    public Process(String PID, int arrTime,
                   int srvTime, int priority, int PIDInt_ ){

        this.PID = PID;
        this.ArrTime = arrTime;
        this.SrvTime = srvTime;
        this.Priority = priority;
        this.PIDInt = PIDInt_;

        this.startTime = 0;
        this.finishTime = 0;



    }



    public int getArrTime() {
        return ArrTime;
    }

    public void setArrTime(int arrTime) {
        ArrTime = arrTime;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getSrvTime() {
        return SrvTime;
    }

    public void setSrvTime(int srvTime) {
        SrvTime = srvTime;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }
    public int getPIDInt(){
        return PIDInt;
    }
    public int getStartTime(){
        return this.startTime;
    }
    public void setStartTime(int start_){
        this.startTime = start_;
    }
    public int getFinishTime(){
        return this.finishTime;
    }
    public void setFinishTime(int finish_){
        this.finishTime = finish_;
    }

}
