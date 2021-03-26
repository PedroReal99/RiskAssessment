/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import webserver.threadController.ThreadController;
import webserver.threadController.ThreadTimeController;

/**
 *
 * @author CarloS
 */
public class ServerThread extends Thread {

    private ServerSocket sock;
    
    private ThreadController thrctrl;
    
    private ThreadTimeController ttctrl;

    /**
     * Cria uma thread the servidos nova
     *
     * @param sock
     * @param t
     */
    public ServerThread(ServerSocket sock, ThreadController t, ThreadTimeController tt) {
        this.sock = sock;
        this.thrctrl = t;
        this.ttctrl = tt;
    }

    @Override
    public void run() {
        Socket cliSock;
        while (true) {
            try {
                cliSock = sock.accept();
                HTTPConnector req = new HTTPConnector(cliSock,thrctrl,ttctrl);
                req.start();
            } catch (IOException ex) {
                //
            }
        }
    }
}
