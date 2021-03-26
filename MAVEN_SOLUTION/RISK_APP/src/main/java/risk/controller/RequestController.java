/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import core.domain.Case.CaseI;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import core.persistence.RepositoryFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import risk.importjson.JsonImport;
/**
 *
 * @author Ricardo Branco
 */
public class RequestController implements Runnable{
    
    /**
     * Thread
     */
    private Thread t;
    
    /**
     * Thread number
     */
    private String thread_number;
    
    /**
     * Path to source file
     */
    private String path;
    
    /**
     * Matrix version
     */
    private String version;
    
    /**
     * True if  the result includes details / false if it don't
     */
    private String details;
    
    /**
     * Constructs an instance of RequestController with the path to the source file
     * @param path_version_details  Source path + matrix version
     * @param thread_number Thread number
     */
    public RequestController(String path_version_details, int thread_number) {
        this.thread_number = String.valueOf(thread_number);
        this.path = path_version_details.substring(0, path_version_details.indexOf(";"));
        this.version = path_version_details.substring(path_version_details.indexOf(";") + 1, path_version_details.lastIndexOf(";"));
        this.details = path_version_details.substring(path_version_details.lastIndexOf(";") + 1, path_version_details.length());
    }
    
    /**
     * Processes the request (run thread)
     */
    public void run() {
//        System.out.println("Running " + thread_number);
//        try {
//            MatrixRepository mr = PersistenceContext.repositories().riskMatrixRepository();
//            RiskMatrix rm = mr.findByName(this.version);
//            JsonImport ji = new JsonImport();
//            CaseI c = ji.createCase(this.path);
//            RiskAssessmentController rac = new RiskAssessmentController();
//            if ("true".equals(this.details)) {
//                rac.calculateRiskAssessment(rm, c, true);
//            } else {
//                rac.calculateRiskAssessment(rm, c, false);
//            }
//            System.out.println("Estado do caso: " + c.obtainState());
//            Thread.sleep(50);
//        } catch (InterruptedException ex) {
//            System.out.println("Request " + thread_number + " interrupted.");
//        } catch (IOException | ParseException ex) {
//            Logger.getLogger(RequestController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("Request " + thread_number + " exiting.");
    }

    /**
     * Starts a new parallel request process (new thread)
     */
    public void processRequest() {
        System.out.println("Starting " + thread_number);
        if (t == null) {
            t = new Thread(this, thread_number);
            t.start();
        }
    }
}
