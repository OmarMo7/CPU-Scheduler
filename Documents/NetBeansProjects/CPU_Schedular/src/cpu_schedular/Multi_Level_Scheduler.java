/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu_schedular;

import static cpu_schedular.Utils.get_arrived_processes;
import static cpu_schedular.Utils.get_first_arrives;
import static cpu_schedular.Utils.get_last_arrives;
import static cpu_schedular.Utils.get_max_priority;
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
public class Multi_Level_Scheduler {

    void readFile(String Path, ArrayList<Process> readyQueue) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(Path);
        BufferedReader br = new BufferedReader(fr);
        int numOfQueue;
        numOfQueue = Integer.parseInt(br.readLine());
        ArrayList<Queue> Q = new ArrayList<>();
        for (int i = 0; i < numOfQueue; i++) {
            String data = br.readLine();
            String[] arr = data.split(" ");
            Queue cur = new Queue(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2])) {
            };
            Q.add(cur);
        }

        for (int j = 0; j < numOfQueue; j++) {

            for (int k = 0; k < Q.get(j).getNumOfProcess(); k++) {

                String data = br.readLine();
                String[] arr = data.split(" ");
                Process p = new Process(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Q.get(j).getAlgo(), Q.get(j).getPriority());
                p.setRemaningTime(p.getBurstTime());
                p.setNewarrivalTime(p.getArrivalTime());
                readyQueue.add(p);

            }
        }

    }

    

    void Write_file(String path, ArrayList<Process> AllProgress, ArrayList<Process> unique) {
        Formatter out = new Formatter();

        try {
            out = new Formatter(path);
        } catch (FileNotFoundException e) {
            System.out.println("Can not find File " + e);
        }

        out.format("Multi-Level_Scheduler - Processes execution order : ");
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

        int cur_time = 0;
        int Quantum = 2; // 34an el RR
        ArrayList<Process> arrived_processes = new ArrayList<>(); // kol eli already weslo 3nd el time dah
        ArrayList<Process> AllProgress = new ArrayList<>(); // kol eli et3mlohom excution 
        ArrayList<Process> unique = new ArrayList<>();

        while (!readyQueue.isEmpty()) {

            get_arrived_processes(readyQueue, arrived_processes, cur_time);
            ArrayList<Process> Max_priority = new ArrayList<>();
            get_max_priority(arrived_processes, Max_priority);
            Process cur_process = new Process();

            if (Max_priority.size() == 1 && !Max_priority.get(0).getAlg().equals("FCFS"))
            {
                cur_process = Max_priority.get(0);

            } else         
            {

                cur_process = get_first_arrives(Max_priority);
            }

            System.out.println("Multi-Level_Scheduler: current process = " + cur_process.getProcessName());

            switch (cur_process.getAlg()) {
                case "FCFS":
                    int i;
                    Process lastArrives = get_last_arrives(readyQueue);

                    if (lastArrives.getPriority() > cur_process.getPriority() && cur_process.getRemaningTime() + cur_time > lastArrives.getArrivalTime()) {
                        i = lastArrives.getArrivalTime() - cur_time;
                        while (i > 0) {
                            cur_time += 1;
                            cur_process.setRemaningTime(cur_process.getRemaningTime() - 1);
                            AllProgress.add(cur_process);
                            i--;
                        }
                    } else {
                        cur_time += cur_process.getRemaningTime();
                        cur_process.setRemaningTime(0);
                        AllProgress.add(cur_process);
                    }
                    break;
                case "RR":
                    if (cur_process.getRemaningTime() > Quantum) {
                        cur_time += Quantum;
                        cur_process.setRemaningTime(cur_process.getRemaningTime() - Quantum);
                        cur_process.setNewarrivalTime(cur_time);
                        AllProgress.add(cur_process); // just added to the excuted orders

                    } else {
                        cur_time += cur_process.getRemaningTime();
                        cur_process.setRemaningTime(0);
                        AllProgress.add(cur_process);
                    }
                    break;
                default:
                    break;
            }

            if (cur_process.getRemaningTime() == 0) {
                cur_process.setEndTime(cur_time);
                int indx = search_by_name(cur_process, readyQueue);
                readyQueue.remove(indx);
            }

            arrived_processes.clear();

        }

        for (int i = 0; i < AllProgress.size(); i++) {
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

        Write_file("Multi_Level_Scheduler_output.txt", AllProgress, unique);

    }

}
