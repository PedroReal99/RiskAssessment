/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http;

import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import static org.springframework.data.jpa.domain.JpaSort.path;
import webserver.applicationSetup.AppSettingsSE;
import webserver.applicationSetup.ApplicationSE;
import webserver.threadController.ThreadController;
import webserver.threadController.ThreadTimeController;

class Server extends Thread {

    static private final int HTTP_PORT = 9504;
    static private final int HTTPS_PORT = 9505;

    //60000 miliseconds = 1 minute
    static private final int PERIOD_VERIFICATION_TIME = 60000;

    static private final int INICIAL_LOAD = 0;

    public static void main(String args[]) throws Exception {

        ServerSocket httpSocket = null;
        SSLServerSocket httpsSocket = null;
        // OPEN SOCKET
        try {
            httpSocket = new ServerSocket(HTTP_PORT);
        } catch (IOException ex) {
            System.out.println("Server failed to open local port " + HTTP_PORT);
            System.exit(1);
        }

        AppSettingsSE settings = ApplicationSE.settings();
        
        System.setProperty("javax.net.ssl.keyStore", getFileResourceDirectory(settings.getKeyStore()));
        System.setProperty("javax.net.ssl.keyStorePassword", settings.getKeyStorePassword());

        try {
            SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            httpsSocket = (SSLServerSocket) sslF.createServerSocket(HTTPS_PORT);
        } catch (IOException ex) {
            System.out.println("Server failed to open local port " + HTTPS_PORT);
            System.exit(1);
        }

        ThreadTimeController tt = new ThreadTimeController(PERIOD_VERIFICATION_TIME);
        ThreadController t = new ThreadController(INICIAL_LOAD, INICIAL_LOAD);

        ServerThread httpServer = new ServerThread(httpSocket, t, tt);
        ServerThread httpsServer = new ServerThread(httpsSocket, t, tt);

        httpServer.start();
        httpsServer.start();
    }

    public static String getFileResourceDirectory(String resource) {
        File file = new File(Server.class.getClassLoader().getResource(resource).getFile());
        String path = file.getParentFile().getParent();
        path += "/classes/"+resource;
        return path.replace("file:", "").replaceAll("%20", " ");
    }
}
