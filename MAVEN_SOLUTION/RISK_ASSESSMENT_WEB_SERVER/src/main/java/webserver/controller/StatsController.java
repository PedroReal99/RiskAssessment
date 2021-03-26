/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import webserver.http.HTTPConnector;
import webserver.http.HTTPmessage;
import webserver.http.ServerThread;

/**
 *
 * @author morei
 */
public class StatsController {

    private static List<HTTPmessage> httpMessages = new ArrayList<>();
    private static List<Date> dates = new ArrayList<>();

    public StatsController() {
    }

    public static void associateBegining(HTTPmessage message, Date date) {
        httpMessages.add(message);
        dates.add(date);
    }

    public static String createBody() {
        String s = "";
        String tmp[], tmp2[];
        for (int i = 0; i < dates.size(); i++) {
            s += "Data " + dates.get(i) + "\n";
            s += " Metodo " + httpMessages.get(i).getMethod() + "\n";
            tmp = httpMessages.get(i).getURI().getUri().split("\\?");
            tmp2 = tmp[1].split("=");
            s += " Uri " + tmp[0] + "\n";
            s += " File " + tmp2[1] + "\n";
        }
        System.out.println(s);
        return s;

        // Uri /riskAssessment/currentload?export=xml
    }

    public static void setHttpMessages(List<HTTPmessage> httpMessages) {
        StatsController.httpMessages = httpMessages;
    }

    public static void setDates(List<Date> dates) {
        StatsController.dates = dates;
    }

    public static List<HTTPmessage> getHttpMessages() {
        return httpMessages;
    }

    public static List<Date> getDates() {
        return dates;
    }

}
