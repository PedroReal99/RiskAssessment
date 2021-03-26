package georeferenceMock;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.GeoreferenceService;
import com.mycompany.georeference_interface.TravelMethod;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author João Flores
 */
public class GeoRefMockService implements GeoreferenceService {

    static final List<GeoRefServiceDTO> HOSPITALS = new ArrayList<>();
    static final List<GeoRefServiceDTO> POLICE = new ArrayList<>();

    static {
        HOSPITALS.add(new GeoRefServiceDTO(0, "Alameda Prof. Hernani Monteiro", "Porto", "Portugal", "4200-319", "Hospital Sao Joao", 41.1814, -8.6000));
        HOSPITALS.add(new GeoRefServiceDTO(0, "R. Dr. Antonio Bernardino de Almeida", "Porto", "Portugal", "4200-072", "IPO", 41.1831, -8.6055));
        HOSPITALS.add(new GeoRefServiceDTO(906, "Rua de Camoes", "Porto", "Portugal", "4049-025", "Hospital Santa Maria", 41.1600, -8.6010));
        HOSPITALS.add(new GeoRefServiceDTO(1121, "Rua de Costa Cabral", "Porto", "Portugal", "4200-215", "Hospital Conde Ferreira", 41.1708, -8.5902));
        HOSPITALS.add(new GeoRefServiceDTO(0, "Rua de Sarmento de Beires", "Porto", "Portugal", "4250-449", "Hospital da Prelada", 41.1705755, -8.6311168));
        HOSPITALS.add(new GeoRefServiceDTO(0, "R. de Dr. Eduardo Torres", "Porto", "Portugal", "4049-025", "Hospital Pedro Hispano", 41.1810976, -8.6670057));

        POLICE.add((new GeoRefServiceDTO(6202, "Avenida Conde", "Porto", "Portugal", "4465-099", "Esquadra PSP de SAO MAMEDE DE INFESTA", 41.1932149, -8.6126346)));
        POLICE.add((new GeoRefServiceDTO(573, "R. Vila Franca", "Porto", "Portugal", "4450-803", "GNR - Guarda Nacional Republicana", 41.190183, -8.7053832)));
        POLICE.add((new GeoRefServiceDTO(0, "Rua 13 - Bairro Rainha Dona Leonor", "Porto", "Portugal", "4150-734", "Municipal police Porto", 41.1500436, -8.6645179)));
        POLICE.add((new GeoRefServiceDTO(781, "R. Chavinha", "Porto", "Portugal", "4460-778", "PSP - Esquadra de Custóias", 41.1995205, -8.6464545)));
        POLICE.add((new GeoRefServiceDTO(101, "R. Dr. Augusto Martins", "Porto", "Portugal", "4470-157", "PSP - Esquadra da Maia", 41.2310984, -8.6245161)));
    }

    public GeoRefMockService() {
    }

    @Override
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod method) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        dto.distance = Math.abs(from.compareTo(to) * 10000);
        dto.duration = Math.abs(dto.distance / 1000);
        return dto;
    }

    @Override
    public GeoRefServiceDTO getFullLocation(String param) {
        Random rand = new Random();
        if ((param.length() & 1) == 0) {
            int n = rand.nextInt(HOSPITALS.size());
            return HOSPITALS.get(n);
        }
        int n = rand.nextInt(POLICE.size());
        return POLICE.get(n);
    }

    @Override
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find) {
        if (find.equalsIgnoreCase("police")) {
            return new HashSet<>(POLICE);
        }
        return new HashSet<>(HOSPITALS);
    }

    @Override
    public GeoRefServiceDTO getElevation(String param) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        Random rand = new Random();
        dto.height =rand.nextInt(10000)/param.length();
        return dto;
    }
    
    @Override
    public double getElevationDifferenceBetweenTwoLocations(String loc1, String loc2) {
        GeoRefServiceDTO loc1value = getElevation(loc1);
        GeoRefServiceDTO loc2value = getElevation(loc2);
        double finalvalue = loc1value.height-loc2value.height;
        return finalvalue;
    }

}
