package webserver.http.requestHandler;

import webserver.http.Config;
import webserver.http.HTTPmessage;
import webserver.utils.FileReceiver;
import webserver.utils.FormatParser;
import webserver.utils.TextFormat;

import java.io.File;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserver.controller.StatsController;

public class GetRiskAssessmentResultRequestHandler implements RequestHandler {

    @Override
    public HTTPmessage handle(HTTPmessage request) {
        StatsController.associateBegining(request,Date.from(Instant.now()));
        System.out.println("Handling request..");
        HTTPmessage response = new HTTPmessage();
        File file = null;

        Map<String, String> parameters = handleHttpExceptions(request, response);
        if (parameters == null) {
            return response;
        }

        String content = request.getContentAsString();
        TextFormat type = FormatParser.getTextFormat(request.getContentType());
        FileReceiver fr = new FileReceiver();

        try {
            try {
                file = fr.fileToMethods(content, TextFormat.XML, "se02");
            } catch (Exception ex) {
                Logger.getLogger(GetRiskAssessmentResultRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            //response.setContent("", type);
            response.setContentFromFile(file.getName());
            response.setResponseStatus(Config.CREATED);
            System.out.println("CREATED");
            return response;

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            response.setResponseStatus(Config.INTERNAL_ERROR);
            System.out.println("INTERNAL ERROR");
            return response;
        }
    }

}
