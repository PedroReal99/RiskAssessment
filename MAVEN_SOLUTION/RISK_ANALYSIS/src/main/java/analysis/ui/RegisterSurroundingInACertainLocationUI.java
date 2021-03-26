/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.SurroundingController;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import core.persistence.PersistenceContext;
import core.persistence.SurroundingRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author joaoflores
 */
public class RegisterSurroundingInACertainLocationUI extends AbstractUI {

    private final SurroundingController surroundingController = new SurroundingController();

    @Override
    protected boolean doShow() {
        String address;
        String stName;
        String category;
        String registerMore = "NO";
        Surrounding surrounding;
        System.out.println(headline());
        do {
            System.out.println("Insert the Surrounding name");
            stName = Console.readLine("Surrounding Type Name:");

            System.out.println("Insert the Surrounding Category");
            category = Console.readLine("Surrounding Type Category:");

            System.out.println("Insert the adress");
            address = Console.readLine("Surrounding Type Address:");

            surrounding = surroundingController.obtainSurroundingFromDB(new STName(category), new SurroundingName(stName));
            if (surrounding == null) {
                System.out.println("Surrounding doesn't exist in our DataBase,please wait a few seconds\n");
                try {
                    surrounding = surroundingController.registerSurroundingByLocation(category, stName, address);
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(RegisterSurroundingInACertainLocationUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Surrounding Info:\n");
            System.out.println("Surrounding Name:\n" + stName);
            System.out.println("Surrounding Address:\n" + address);
            System.out.println("Surrounding Location:\n");
            String coordenates[] = surrounding.obtainLocation().getLocation().split(",");
            System.out.println(Arrays.toString(coordenates));

            System.out.println("Is the information correct?\n");
            String response = Console.readLine("YES(Y)|NO(N):");

            if (response.equalsIgnoreCase("NO") || response.equalsIgnoreCase("N")) {
                //RESET THE COORDENATES MANUALLY!!!
                System.out.println("Insert the Surrounding Latitude\n");
                float latitude = Float.parseFloat(Console.readLine("Latitude:"));

                System.out.println("Insert the Surrounding Longitude\n");
                float longitude = Float.parseFloat(Console.readLine("Longitude:"));

                System.out.println("Insert the Surrounding Altitude\n");
                float height = Float.parseFloat(Console.readLine("Altitude:"));

                surrounding.obtainLocation().setLatitude(latitude);
                surrounding.obtainLocation().setLongitude(longitude);
                surrounding.obtainLocation().setAltitude(height);
            }
            //IN CASE EVERYTHING IS OK OR RIGHT AFTER RESET THE COORDENATES!!!
            coordenates = surrounding.obtainLocation().getLocation().split(",");
            System.out.println(Arrays.toString(coordenates));
            boolean show = false;
            try {
                show = surroundingController.showAerealImagemFromLocation(address);
            } catch (IOException ex) {
                Logger.getLogger(RegisterSurroundingInACertainLocationUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (show == true) {
                Surrounding save = surroundingController.RegisterSurrounding(surrounding);
                if (save == null) {
                    System.out.println("UPS....SOMETHING WENT WRONG WHILE REGISTING THE SURROUNDING!");
                } else {
                    System.out.println("SURRONDING REGISTED SUCCESSFULLY!");
                }

            }

            System.out.println("Do you want to register more surroundings?");
            registerMore = Console.readLine("YES(Y)|NO(N):");
        } while (registerMore.equalsIgnoreCase("YES") || registerMore.equalsIgnoreCase("Y"));
        return true;
    }

    @Override
    public String headline() {
        return "Register Surrounding In A Certain Location:";
    }

}
