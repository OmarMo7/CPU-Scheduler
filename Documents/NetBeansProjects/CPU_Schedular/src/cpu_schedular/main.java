/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu_schedular;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Omar Mostafa
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        ArrayList<Process> readyQueue = new ArrayList<>();
        SJF_Scheduler s = new SJF_Scheduler();
        s.readFile("SJF_Scheduler_input.txt", readyQueue);
        s.Excute(readyQueue);
        CPU_Scheduler c = new CPU_Scheduler();
        c.readFile("Multi_Level_Scheduler_input.txt", readyQueue);
        c.Excute(readyQueue);
        
    }
    
}
