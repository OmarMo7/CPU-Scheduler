/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu_schedular;

import static cpu_schedular.Utils.search_by_name;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

/**
 *
 * @author Omar Mostafa
 */
public class RR_Scheduler {

    void readFile(String Path, ArrayList<Process> readyQueue) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(Path);
        BufferedReader br = new BufferedReader(fr);
        int numOfProcesses;
        numOfProcesses = Integer.parseInt(br.readLine());

        for (int i = 0; i < numOfProcesses; i++) {
            String data = br.readLine();
            String[] arr = data.split(" ");
            Process newProcess = new Process(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), "RR", 1);
            readyQueue.add(newProcess);
        }

    }

   
    void Write_file(String path, ArrayList<Process> AllProgress, ArrayList<Process> unique) {
        Formatter out = new Formatter();

        try {
            out = new Formatter(path);
        } catch (FileNotFoundException e) {
            System.out.println("Can not find File " + e);
        }

        out.format("Round-Robin_Scheduler - Processes execution order : ");
        out.format("%n");
        for (int i = 0; i < AllProgress.size(); i++) {
            out.format("%s", AllProgress.get(i).getProcessName());
            out.format("%n");
        }

        out.format("----------------------");
        out.format("%n");
        out.format("%n");
        out.format("Turnaround time : ");
        out.format("%n");
        double total = 0;
        for (int i = 0; i < unique.size(); i++) {
            out.format("%s", unique.get(i).getProcessName() + "  " + unique.get(i).getTurnAroundTime());
            total += unique.get(i).getTurnAroundTime();
            out.format("%n");
        }

        out.format("----------------------");
        out.format("%n");

        out.format("Avg turnaround time = " + total / unique.size());

        ///////////////////////////////////////
        out.format("%n");
        out.format("%n");
        out.format("Waiting time : ");
        out.format("%n");
        double totalWaitingAvg = 0;
        for (int i = 0; i < unique.size(); i++) {
            out.format("%s", unique.get(i).getProcessName() + "  " + unique.get(i).getWaitingTime());
            totalWaitingAvg += unique.get(i).getWaitingTime();
            out.format("%n");
        }

        out.format("----------------------");
        out.format("%n");

        out.format("Avg Waiting time = " + totalWaitingAvg / unique.size());

        out.flush(); // 34an yktb 3al file
        out.close();
    }

    void Excute(ArrayList<Process> readyQueue) {
        int index = 0;
        int cur_time = 0;
        int Quantum = 2; // 34an el RR
        ArrayList<Process> arrived_processes = new ArrayList<>(); // kol eli already weslo 3nd el time dah
        ArrayList<Process> AllProgress = new ArrayList<>(); // kol eli et3mlohom excution 
        ArrayList<Process> unique = new ArrayList<>();
        for (int i = 0; i < readyQueue.size(); i++) {
            readyQueue.get(i).setRemaningTime(readyQueue.get(i).getBurstTime());
        }
        while (!readyQueue.isEmpty()) {

//            get_arrived_processes(readyQueue, arrived_processes, cur_time);
            if (index > readyQueue.size() - 1)index = 0;
            Process cur_process = readyQueue.get(index);
            
            System.out.println("Round-Robin_Scheduler: current process = " + cur_process.getProcessName());

            if (cur_process.getRemaningTime() > Quantum) {
                cur_time += Quantum;
                cur_process.setRemaningTime(cur_process.getRemaningTime() - Quantum);
                // just added to the excuted orders
            } else {
                cur_time += cur_process.getRemaningTime();
                cur_process.setRemaningTime(0);

            }
            AllProgress.add(cur_process);
            if (cur_process.getRemaningTime() == 0) {
                cur_process.setEndTime(cur_time);
                int indx = search_by_name(cur_process, readyQueue);
                readyQueue.remove(indx);

            }

            arrived_processes.clear();
            index++;

        }

        for (int i = 1; i < AllProgress.size(); i++) {
            if (!unique.contains(AllProgress.get(i))) {
                unique.add(AllProgress.get(i));
            }
        }

        for (int i = 0; i < unique.size(); i++) {
            for (int j = 0; j < AllProgress.size(); j++) {
                if (unique.get(i).getProcessName() == AllProgress.get(j).getProcessName()) {
                }
            }
            //TODO calc the waiting time right here == endtime - (arrival + burst)
            unique.get(i).setWaitingTime(unique.get(i).getEndTime() - (unique.get(i).getArrivalTime() + unique.get(i).getBurstTime()));
            unique.get(i).setTurnAroundTime(unique.get(i).getWaitingTime() + unique.get(i).getBurstTime());
        }

        Write_file("Round-Robin_Scheduler_output.txt", AllProgress, unique);

    }
}
