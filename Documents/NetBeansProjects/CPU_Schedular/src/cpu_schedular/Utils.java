/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu_schedular;

import java.util.ArrayList;

/**
 *
 * @author Omar Mostafa
 */
public class Utils {

    static void get_arrived_processes(ArrayList<Process> readyQueue, ArrayList<Process> arrived_processes, int time) {
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).getArrivalTime() <= time) {
                arrived_processes.add(readyQueue.get(i));
            }
        }
    }

    static Process get_min_remaining(ArrayList<Process> Max_priority) {

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

    static void get_max_priority(ArrayList<Process> arrived_processes, ArrayList<Process> max) {

        int max_priority = arrived_processes.get(0).getPriority();

        for (int j = 0; j < arrived_processes.size(); j++) {
            if (arrived_processes.get(j).getPriority() > max_priority) {
                max_priority = arrived_processes.get(j).getPriority();
            }
        }

        for (int j = 0; j < arrived_processes.size(); j++) // to add max priority 
        {
            if (arrived_processes.get(j).getPriority() == max_priority) {
                max.add(arrived_processes.get(j));
            }
        }

    }

    static Process get_min_burst_time(ArrayList<Process> Max_priority) {

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

    static Process get_first_arrives(ArrayList<Process> Max_priority) {

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

    static Process get_last_arrives(ArrayList<Process> Max_priority) {

        Process cur_process = new Process();
        int max_arrival_time = Max_priority.get(Max_priority.size() - 1).getNewarrivalTime();
        cur_process = Max_priority.get(Max_priority.size() - 1);

        for (Process Max_priority1 : Max_priority) {
            for (int j = 0; j < Max_priority.size(); j++) {
                if (Max_priority1.getNewarrivalTime() > max_arrival_time) {
                    max_arrival_time = Max_priority.get(j).getNewarrivalTime();
                    cur_process = Max_priority.get(j);
                }
            }
        }

        return cur_process;
    }

    static int search_by_name(Process a, ArrayList<Process> readyQueue) {
        int indx = -1;

        for (int i = 0; i < readyQueue.size(); i++) {
            if (a.getProcessName().equals(readyQueue.get(i).getProcessName())) {
                indx = i;
                break;
            }
        }
        return indx;
    }
    
    static Process get_max_priority(ArrayList<Process> arrived_processes) {

        Process max_priority = arrived_processes.get(0);

        for (int j = 0; j < arrived_processes.size(); j++) {
            if (arrived_processes.get(j).getPriority() < max_priority.getPriority()) {
                max_priority = arrived_processes.get(j);
            }
        }
        return max_priority;

    }
}
