package webserver.http;

import webserver.applicationSetup.ApplicationSE;
import webserver.http.requestHandler.RequestHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import webserver.threadController.ThreadTimeController;
import webserver.applicationSetup.AppSettingsSE;
import webserver.controller.StatsController;
import webserver.threadController.ThreadController;

public class HTTPConnector extends Thread {

    public static volatile ThreadTimeController thread_time_controller;
    
    public static final Map<String, RequestHandler> SWITCHER;

    private static volatile ThreadController thrctrl;
    
    static {
        SWITCHER = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            String knownHandlers=ApplicationSE.settings().getClassLoaderPath();
            Document handlers = builder.parse(HTTPConnector.class.getClassLoader().getResourceAsStream(knownHandlers));
            NodeList handlerList = handlers.getDocumentElement().getElementsByTagName("handler");
            
            
            for (int i = 0; i < handlerList.getLength(); i++) {
                String key = "";
                key += ((Element) handlerList.item(i)).getElementsByTagName("method").item(0).getTextContent().toUpperCase();
                key += " " + ((Element) handlerList.item(i)).getElementsByTagName("uri").item(0).getTextContent().toUpperCase();
                String className = ((Element) handlerList.item(i)).getElementsByTagName("className").item(0).getTextContent();
                SWITCHER.put(key, (RequestHandler) Class.forName(className).newInstance());
            }

        } catch (ParserConfigurationException | SAXException | IOException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(HTTPConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    String baseFolder;
    Socket sock;
    DataInputStream inS;
    DataOutputStream outS;

    public HTTPConnector(Socket cliSock,ThreadController t,ThreadTimeController tt) {
        this.sock = cliSock;
        HTTPConnector.thrctrl = t;
        HTTPConnector.thread_time_controller = tt;
    }

    @Override
    public void run() {
        try {
            outS = new DataOutputStream(sock.getOutputStream());
            inS = new DataInputStream(sock.getInputStream());
        } catch (IOException ex) {
            System.out.println("Thread error on data streams creation");
        }
        HTTPmessage request = null;
        try {
            request = new HTTPmessage(inS);
        } catch (IOException ex) {
            System.out.println("Thread error when reading request");
        }
        try {
            verifyServerLoad();
        } catch (InterruptedException ex) {
            try {
                HTTPmessage response = new HTTPmessage();
                response.setResponseStatus(Config.INTERNAL_ERROR);
                response.send(outS);
                sock.close();
            } catch (IOException ex1) {
                System.out.println("Thread error when writing reply");      
            }
        }
        if (request != null) {
            HTTPConnector.thrctrl.addLoad();
            HTTPConnector.thread_time_controller.addThreadExecutionTime(new Date());
            updateCurrentLoadPerMinute();
            String key=request.getMethod().trim()+" "+request.getURI().getQuerylessURI().trim();
            setPriorityForThreads(request.getURI().getQuerylessURI().trim());
            System.out.println("key is "+key);
             HTTPmessage response;
            try {
                response=SWITCHER.get(key.toUpperCase()).handle(request);
            }
            catch (NullPointerException ex){
                response=new HTTPmessage();
                response.setResponseStatus(Config.NOT_FOUND);
                HTTPConnector.thrctrl.removeLoad();
            }
            try {
                response.send(outS);
            } catch (IOException ex) {
                System.out.println("Thread error when writing reply");
            }
            HTTPConnector.thrctrl.removeLoad();
        }
        try {
            sock.close();
            StatsController.createBody();
        } catch (IOException ex3) {
            System.out.println("CLOSE IOException");
        }
    }
    
    public void verifyServerLoad() throws InterruptedException {
        AppSettingsSE setting = new AppSettingsSE();
        updateCurrentLoadPerMinute();
        if(HTTPConnector.thrctrl.getLoad() >= setting.getMaxRequestForServer() || HTTPConnector.thrctrl.getLoadPerMinute() >= setting.getMaxRequestPerMinuteForServer()) {
            throw new InterruptedException("Unable to process server request");
        }
    }
    
    protected static void setPriorityForThreads(String func) {
        switch(func) {
            case "/riskAssessment/currentload": Thread.currentThread().setPriority(MAX_PRIORITY);
                                                break;
                                                
            case "/riskAssessment/cases/submit/multiple": Thread.currentThread().setPriority(MIN_PRIORITY);
                                                          break;   
                                                          
            default: Thread.currentThread().setPriority(NORM_PRIORITY); //Just to make code readable, default value is 5 = NORM_PRIORITY
                     break;                                              
        }
    }
    
    protected static void updateCurrentLoadPerMinute() {
        thread_time_controller.verifyThreadsDate();
        HTTPConnector.thrctrl.updateLoadPerMinute(thread_time_controller.numThreadsRunning());
    }
    
    public static ThreadController obtainThreadController() {
        return HTTPConnector.thrctrl;
    }
}
