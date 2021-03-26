/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.SurroundingController;
import core.domain.Surrounding.Surrounding;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.util.ArrayList;

/**
 *
 * @author caduc
 */
public class SurroundingUI extends AbstractUI {

    private final SurroundingController c = new SurroundingController();

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String option;
        String district;
        String lat1;
        String lat2;
        String lon1;
        String lon2;
        String file;

        Iterable<Surrounding> list = new ArrayList<>();
        do {
            option = Console.readLine("1-List all\n2-List by district\n3-List by coordinates\n4-Export to XHTML file\n0-Exit\n\nChoose option:");

            switch (option) {
                case "1":
                    list = c.listSurroundings();
                    break;
                case "2":
                    district = Console.readLine("District:");
                    list = c.listSurroundings(district);
                    break;
                case "3":
                    lat1 = Console.readLine("Latitude 1:");
                    lat2 = Console.readLine("Latitude 2:");
                    lon1 = Console.readLine("Longitude 1:");
                    lon2 = Console.readLine("Longitude 2:");
                    list = c.listSurroundings(lat1, lon1, lat2, lon2);
                    break;
                case "4":
                    file = Console.readLine("File:");
                    c.exportToXHTML(list, file);
                case "0":
                    (new ExitUIAction()).execute();
            }
            for (Surrounding s : list) {
                System.out.println(s.obtainSName().toString());
            }
        } while ("1".equals(option) || "2".equals(option) || "3".equals(option));
        return true;
    }

    @Override
    public String headline() {
        return "List Surroundings:";
    }

}
