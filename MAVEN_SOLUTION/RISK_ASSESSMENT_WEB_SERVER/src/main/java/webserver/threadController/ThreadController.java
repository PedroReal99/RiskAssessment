/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.threadController;

/**
 *
 * @author Vasco Rodrigues
 */
public class ThreadController {
    
    private int current_load;
    
    private int current_load_per_minute;
    
    public ThreadController(int load, int load_minute) {
        if(load < 0 || load_minute < 0) {
            throw new IllegalArgumentException();
        }
        this.current_load = load;
        this.current_load_per_minute = load_minute;
    }
    
    public void addLoad() {
        synchronized(this) {
            current_load++;
        }
    }
    
    public void removeLoad() {
        synchronized(this) {
            current_load--;
        }
    }
    
    public void updateLoadPerMinute(int i) {
        synchronized(this) {
            current_load_per_minute = i;
        }
    }
    
    public int getLoad() {
        synchronized(this) {
            return current_load;
        }
    }
    
    public int getLoadPerMinute() {
        synchronized(this) {
            return current_load_per_minute;
        }
    }
    
}
