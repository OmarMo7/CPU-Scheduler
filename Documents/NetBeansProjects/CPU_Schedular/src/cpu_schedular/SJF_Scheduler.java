/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu_schedular;

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
public class SJF_Scheduler {

    void readFile(String Path, ArrayList<Process> readyQueue) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(Path);
        BufferedReader br = new BufferedReader(fr);
        int numOfProcesses;
        numOfProcesses = Integer.parseInt(br.readLine());

        for (int i = 0; i < numOfProcesses; i++) {
            String data = br.readLine();
            String[] arr = data.split(" ");
            Process newProcess = new Process(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), "SJF", 1);
            readyQueue.add(newProcess);
        }

    }

    void get_arrived_processes(ArrayList<Process> readyQueue, ArrayList<Process> arrived_processes, int time) {
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).getArrivalTime() <= time) {
                arrived_processes.add(readyQueue.get(i));
            }
        }
    }

    Process get_min_burst_time(ArrayList<Process> Max_priority) {

        Process cur_process = new Process();
        int min_burst_time = Max_priority.get(0).getBurstTime();

        cur_process = Max_priority.get(0);

        for (int j = 0; j < Max_priority.size(); j++) {
            if (Max_priority.get(j).getBurstTime() < min_burst_time) {
                min_burst_time = Max_priority.get(j).getBurstTime();
                cur_process = Max_priority.get(j);
            }
        }

        return cur_process;

    }

    Process get_min_remaining(ArrayList<Process> Max_priority) {

        Process cur_process = new Process();
        int min_remaining = Max_priority.get(0).getRemaningTime();

        cur_process = Max_priority.get(0);

        for (int j = 0; j < Max_priority.size(); j++) {
            if (Max_priority.get(j).getRemaningTime() < min_remaining) {
                min_remaining = Max_priority.get(j).getRemaningTime();
                cur_process = Max_priority.get(j);
            }
        }

        return cur_process;

    }

    int search_by_name(Process a, ArrayList<Process> readyQueue) {
        int indx = -1;

        for (int i = 0; i < readyQueue.size(); i++) {
            if (a.getProcessName().equals(readyQueue.get(i).getProcessName())) {
                indx = i;
                break;
            }
        }
        return indx;
    }

    void Write_file(String path, ArrayList<Process> done, ArrayList<Process> unique) {
        Formatter out = new Formatter();

        try {
            out = new Formatter(path);
        } catch (FileNotFoundException e) {
            System.out.println("Can not find File " + e);
        }

        out.format("Processes execution order : ");
        out.format("%n");
        for (int i = 0; i < done.size(); i++) {
            out.format("%s", done.get(i).getProcessName());
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

    Process get_first_arrives(ArrayList<Process> Max_priority) {

        Process cur_process = new Process();
        int min_arrival_time = Max_priority.get(0).getNewarrivalTime();
        cur_process = Max_priority.get(0);

        for (Process Max_priority1 : Max_priority) {
            for (int j = 0; j < Max_priority.size(); j++) {
                if (Max_priority1.getNewarrivalTime() < min_arrival_time) {
                    min_arrival_time = Max_priority.get(j).getNewarrivalTime();
                    cur_process = Max_priority.get(j);

                }
            }
        }

        return cur_process;

    }

    void Excute(ArrayList<Process> readyQueue) {
        int current_time = 0;
        ArrayList<Process> arrived_processes = new ArrayList<>();
        ArrayList<Process> AllProgress = new ArrayList<>();
        ArrayList<Process> unique = new ArrayList<>();

        while (!readyQueue.isEmpty()) {
            Process cur_process = new Process();
            Process min_process = new Process();
            for (int i = 0; i < readyQueue.size(); i++) {
                readyQueue.get(i).setRemaningTime(readyQueue.get(i).getBurstTime());
            }
            int sumOfAllBursts = 0;
            for (int k = 0; k < readyQueue.size(); k++) {
                sumOfAllBursts += readyQueue.get(k).getBurstTime();
            }
            while (sumOfAllBursts > current_time) {
                get_arrived_processes(readyQueue, arrived_processes, current_time);

                if (arrived_processes.isEmpty()) {
                    current_time += 1;
                    continue;
                }
                if (arrived_processes.size() > 1) {
                    current_time += 1;
                    cur_process = get_first_arrives(arrived_processes);
                    min_process = get_min_remaining(arrived_processes);
                    if (cur_process.getProcessName() == min_process.getProcessName()) {
                        AllProgress.add(cur_process);
                        cur_process.setRemaningTime(cur_process.getRemaningTime() - 1);
                    } else {
                        cur_process = min_process;
                        AllProgress.add(cur_process);
                        cur_process.setRemaningTime(cur_process.getRemaningTime() - 1);
                    }
                    if (cur_process.getRemaningTime() == 0) {
                        cur_process.setEndTime(current_time);
                        int index = search_by_name(cur_process, readyQueue);
                        readyQueue.remove(index);
                    }
                    System.out.println("SJF_Scheduler: current process = " + cur_process.getProcessName());

                } else if (arrived_processes.size() == 1) {
                    current_time += 1;
                    cur_process = arrived_processes.get(0);
                    AllProgress.add(cur_process);
                    cur_process.setRemaningTime(cur_process.getRemaningTime() - 1);
                    if (cur_process.getRemaningTime() == 0) {
                        cur_process.setEndTime(current_time);
                        int index = search_by_name(cur_process, readyQueue);
                        readyQueue.remove(index);
                    }
                }
                System.out.println("SJF_Scheduler: current process = " + cur_process.getProcessName());

                arrived_processes.clear();
            }
        }

        for (int i = 1; i < AllProgress.size(); i++) {
            if (AllProgress.get(i).getProcessName() != AllProgress.get(i - 1).getProcessName()) {
                unique.add(AllProgress.get(i - 1));
            }
        }
        unique.add(AllProgress.get(AllProgress.size() - 1));

        for (int i = 0; i < unique.size(); i++) {
            for (int j = 0; j < AllProgress.size(); j++) {
                if (unique.get(i).getProcessName() == AllProgress.get(j).getProcessName()) {
                }
            }
            //TODO calc the waiting time right here == endtime - (arrival + burst)
            unique.get(i).setWaitingTime(unique.get(i).getEndTime() - (unique.get(i).getArrivalTime() + unique.get(i).getBurstTime()));
            unique.get(i).setTurnAroundTime(unique.get(i).getWaitingTime() + unique.get(i).getBurstTime());
        }

        Write_file(
                "SJF_Scheduler_output.txt", AllProgress, unique);
    }

}
