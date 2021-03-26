/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.threadController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vasco Rodrigues
 */
public class ThreadTimeController {
    
    protected List<Date> threads_execution_time;

    private final int period;
    
    public ThreadTimeController(int period) {
        if(period <= 0) {
            throw new IllegalArgumentException();
        }
        this.period = period;
        threads_execution_time = new ArrayList<>();
    }
    
    public void addThreadExecutionTime(Date d) {
        synchronized(this) {
           threads_execution_time.add(d); 
        }
    }
    
    public void verifyThreadsDate() {
        long verif = new Date().getTime();
        List<Date> temp = new ArrayList<>();
        synchronized(this) {
            threads_execution_time.stream().filter((da) -> (verif-da.getTime() >= period)).forEachOrdered(temp::add);
            temp.forEach((d) -> {
                threads_execution_time.remove(d);
            });
        }
    }
    
    public int numThreadsRunning() {
        synchronized(this) {
            return this.threads_execution_time.size();
        }
    }
}
