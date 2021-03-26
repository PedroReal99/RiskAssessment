package analysis.ui;

import analysis.controller.CaseController;
import analysis.controller.LoginController;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewCasesValidatedByMeUI extends AbstractUI {

    private final CaseController cc = new CaseController();
    private final LoginController lc = new LoginController();

    @Override
    protected boolean doShow() {
        String output = "";
        String summary = "";
        while (true) {
            System.out.println(headline());
            String option = Console.readLine("");
            if (option.equalsIgnoreCase("1")) {
                List<CaseI> list = cc.listCasesValidatedByAR(null);
                System.out.println(cc.makeFormatedString(list));
                System.out.println("\n" + cc.buildSummary());
            } else if (option.equalsIgnoreCase("2")) {
                List<CaseDate> dates = new ArrayList<>();
                System.out.println("\n\nInsert first date: \n");
                String year1 = Console.readLine("Year:");
                String month1 = Console.readLine("Month:");
                String day1 = Console.readLine("Day:");
                System.out.println("\n\nInsert last date: \n");
                String year2 = Console.readLine("Year:");
                String month2 = Console.readLine("Month:");
                String day2 = Console.readLine("Day:");
                dates.add(new CaseDate(year1 + "-" + month1 + "-" + day1 + " 00:00:00"));
                dates.add(new CaseDate(year2 + "-" + month2 + "-" + day2 + " 00:00:00"));
                List<CaseI> list = cc.listCasesValidatedByAR(dates);
                output = cc.makeFormatedString(list);
                summary = cc.buildSummary();
                System.out.println(output);
                System.out.println(summary);
            } else if (option.equalsIgnoreCase("0")) {
                return true;
            } else {
                System.out.println("Wrong number!");
            }
            String input = Console.readLine("\n1 - Export result to xhtml." +
                    "\n0 - Exit");
            if (input.equalsIgnoreCase("1")) {
                String filename = Console.readLine("Insert desired file name (without file type)");
                cc.convertToXHTML(output, summary, filename);
            }
        }
    }

    @Override
    public String headline() {
        return "List cases Validated by me menu:" +
                "\n\n1 - Normal search" +
                "\n2 - Restrict by date" +
                "\n0 - Exit" +
                "\n Choose Option:";
    }
}
