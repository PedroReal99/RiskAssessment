/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import java.time.Instant;
import java.util.Date;
import webserver.controller.StatsController;
import webserver.http.HTTPmessage;

/**
 *
 * @author CarloS
 */
public class TestRequestHandler implements RequestHandler{

    @Override
    public HTTPmessage handle(HTTPmessage request) {
        StatsController.associateBegining(request,Date.from(Instant.now()));
        System.out.println("Handling request..");
        return null;
    }
    
}
