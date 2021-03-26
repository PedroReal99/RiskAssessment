/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.utils;

import core.domain.Case.CaseDate;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Pedro
 */
public class TimePassed {

    /**
     * Calculates the time passed since the date and now
     *
     * @param cd
     * @return
     */
    public static String TimePassed(String cd) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = format.parse(cd);

        

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        
        long mili = now.getTime() - date.getTime();
        Date nDate = new Date(mili);
        return formatter.format(nDate);

        
    }
    
    
//    public static void main(String[] args) throws ParseException {
//        String str = TimePassed("12-06-2019 18:44:00");
//        System.out.println(str);
//    }

}
