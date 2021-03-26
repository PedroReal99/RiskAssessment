/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import core.domain.Coverage.Coverage;
import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author morei
 */
public class SendEmailController {

    public SendEmailController() {
    }

    /**
     *
     * @param props1
     * @param coverage
     * @return
     */
    public static boolean send(Properties props1, Coverage coverage) {
        String host = obtainValueFromProperties(props1, "mail.smtp.host");
        String from = obtainValueFromProperties(props1, "mail.user");
        String pass = obtainValueFromProperties(props1, "mail.password");

        String to = obtainValueFromProperties(props1, "main.to");

        String subject = obtainValueFromProperties(props1, "mail.subject");
        String messageText = obtainValueFromProperties(props1, "mail.body") + " " + coverage.obtainName();

        Properties props = obtainProperties(host, from, pass);

        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject);
            msg.setText(messageText);
            Transport tranport = session.getTransport("smtp");
            tranport.connect(host, from, pass);

            tranport.sendMessage(msg, msg.getAllRecipients());
            tranport.close();
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    public static String obtainValueFromProperties(final Properties props1, String key) {
        return props1.getProperty(key);
    }

    public static Properties obtainProperties(String host, String from, String pass) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        return props;
    }
}
