/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu_schedular;

/**
 *
 * @author Omar Mostafa
 */
public class Process {

    private String processName; //
    private String Alg; //
    private int arrivalTime;
    private int waitingTime;// 
    private int NewarrivalTime; // 

    public int getNewarrivalTime() {
        return NewarrivalTime;
    }
    private int burstTime; //
    private int turnAroundTime;
    private int remaningTime;

    public void setNewarrivalTime(int NewarrivalTime) {
        this.NewarrivalTime = NewarrivalTime;
    }
    private int endTime;
    private int Priority;

    public Process() {
    }

    ;
        
    public Process(String processName, int arrivalTime, int burstTime, String Alg, int Priority) {
        this.processName = processName;
        this.Alg = Alg;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.Priority = Priority;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getAlg() {
        return Alg;
    }

    public void setAlg(String Alg) {
        this.Alg = Alg;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getRemaningTime() {
        return remaningTime;
    }

    public void setRemaningTime(int remaningTime) {
        this.remaningTime = remaningTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int Priority) {
        this.Priority = Priority;
    }
}
