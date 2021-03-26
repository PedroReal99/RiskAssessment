/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webserver.controller.RASubmissionController;
import webserver.http.Config;
import webserver.http.HTTPmessage;
import webserver.http.requestHandler.RASubmissionRequestHandler;

/**
 *
 * @author Ricardo Branco
 */
public class ProcessRequest implements Runnable {

    private Thread t;
    private int thread_number;
    private Element element;
    private RASubmissionController raSubmissionController;
    private String api;

    public ProcessRequest(Element element, RASubmissionController raSubmmissionController, String api, int thread_number) {
        this.element = element;
        this.raSubmissionController = raSubmmissionController;
        this.api = api;
        this.thread_number = thread_number;
    }

    @Override
    public void run() {
        NodeList iObjects = element.getElementsByTagName("insuranceObject");
        RASubmissionRequestHandler.parseInsuranceObjects(raSubmissionController, iObjects);
        System.out.println("CREATED");
        raSubmissionController.save();
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, String.valueOf(thread_number));
            t.start();
        }
    }
}
