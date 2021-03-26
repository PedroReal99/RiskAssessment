package config.ui;

import georeferenceMock.GeoRefMockController;
import com.mycompany.georeference_interface.GeoRefServiceDTO;

import core.domain.location.GPSLocation;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class SurroundingTypeByLocationUI extends AbstractUI {

    private final GeoRefMockController c = new GeoRefMockController();

    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String l1;
        String l2;
        String l3;
        do {
            l1 = Console.readLine("Insert The Latitude:");
            l2 = Console.readLine("Insert The Longitude:");
            l3 = Console.readLine("Insert The Altitude:");
        } while (l1.trim().isEmpty() || l2.trim().isEmpty());

        GPSLocation location = new GPSLocation(Float.parseFloat(l1), Float.parseFloat(l2), Float.parseFloat(l3), "Porto");

        for (GeoRefServiceDTO surrounding : c.getSurroundings(location.toString(), "hospital")) {
            System.out.println(surrounding.name);
        }
        return true;
    }

    @Override
    public String headline() {
        return "Surrounding Types By Location:";
    }

}
