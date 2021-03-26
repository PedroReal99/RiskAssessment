package georeferenceMock;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.GeoreferenceService;
import com.mycompany.georeference_interface.TravelMethod;
import eapli.framework.application.Controller;
import java.util.Set;

public class GeoRefMockController implements Controller, GeoreferenceService {

    private GeoRefMockService grs;

    public GeoRefMockController() {
        grs = new GeoRefMockService();
    }

    @Override
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod method) {
        return grs.getDistanceAndDuration(from, to, method);
    }

    @Override
    public GeoRefServiceDTO getFullLocation(String param) {
        return grs.getFullLocation(param);
    }

    @Override
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find) {
        return grs.getSurroundings(center, find);
    }

    @Override
    public GeoRefServiceDTO getElevation(String param) {
        return grs.getElevation(param);
    }

    @Override
    public double getElevationDifferenceBetweenTwoLocations(String loc1, String loc2) {
        return grs.getElevationDifferenceBetweenTwoLocations(loc1, loc2);
    }
}
