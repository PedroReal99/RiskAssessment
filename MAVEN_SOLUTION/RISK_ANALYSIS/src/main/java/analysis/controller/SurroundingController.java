package analysis.controller;

import analysis.utils.ExportUtil;
import analysis.utils.RegisterSurroundingByLocation;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import core.persistence.PersistenceContext;
import core.persistence.SurroundingRepository;
import georeference.GeoreferenceServiceController;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ricardo Branco
 */
public class SurroundingController {

    private RegisterSurroundingByLocation registerSurroundingByLocation;
    private GeoreferenceServiceController externalGeoRefController;
    private final SurroundingRepository surroundingRepository = PersistenceContext.repositories().surroundingRepository();

    public SurroundingController() {
        registerSurroundingByLocation = new RegisterSurroundingByLocation();
        externalGeoRefController = new GeoreferenceServiceController();
        //do nothing
    }

    public Iterable<Surrounding> listSurroundings() {
        return PersistenceContext.repositories().surroundingRepository().findAll();
    }

    public Iterable<Surrounding> listSurroundings(String district) {
        return PersistenceContext.repositories().surroundingRepository().findByDistrict(district);
    }

    public Iterable<Surrounding> listSurroundings(String lat1, String lon1, String lat2, String lon2) {
        return PersistenceContext.repositories().surroundingRepository().findByCoordinates(lat1, lon1, lat2, lon2);
    }

    public void exportToXHTML(Iterable<Surrounding> list, String filename) {
        ExportUtil util = new ExportUtil(list, filename);
        util.exportToXHTML();
    }

    public Surrounding registerSurroundingByLocation(String category, String stName, String address) throws IOException, ParseException {
        return registerSurroundingByLocation.obtainSurroundingByLocation(category, stName, address);
    }

    /**
     *
     * @param address adress of the surrounding or coordenates
     * @return true if runs correctly false otherwise
     * @throws IOException
     */
    public boolean showAerealImagemFromLocation(String address) throws IOException {
        return externalGeoRefController.openStaticImage(address);
    }

    /**
     *
     * @param surrounding save the surrounding created
     * @return surroudning
     */
    public Surrounding RegisterSurrounding(Surrounding surrounding) {
        return PersistenceContext.repositories().surroundingRepository().save(surrounding);
    }

    /**
     *
     * @param stName type of surrounding
     * @param surroundingName name of the surrounding
     * @return surroudning
     */
    public Surrounding obtainSurroundingFromDB(STName stName, SurroundingName surroundingName) {
        return surroundingRepository.findByBothParameters(stName, surroundingName);
    }
}
