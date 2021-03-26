/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.main;

import risk.controller.RequestController;

/**
 *
 * @author Ricardo Branco
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
         * Passing paths to files as arguments
         */
        for (int i = 0; i < args.length; i++) {
            RequestController r = new RequestController(args[i], i + 1);
            r.processRequest();
        }
    }
    
}
