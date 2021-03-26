/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskFactors;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.TravelMethod;
import core.domain.RiskMatrixs.Attributes.Designation;
import core.dto.GeoRefInfoDTO;
import core.utils.GeoReferenceUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author CarloS
 */
public enum Metric implements MetricStrategy {
    DISTANCIA {
        @Override
        public String getMetrics() {
            return "Distancia";
        }

        @Override
        public String toString() {
            return getMetrics();
        }

        @Override
        public Designation getClassification(String source, List<String> locations, RiskFactor risk) {
            try {
                GeoReferenceUtil geo = new GeoReferenceUtil();

                if (locations.isEmpty()) {
                    locations.addAll(geo.getSurroundingsAsString(source, risk.obtainSTName()));
                }

                List<GeoRefServiceDTO> dtoList = new ArrayList<>();
                for (String local : locations) {
                    GeoRefServiceDTO dto = new GeoRefServiceDTO();
                    dto.name = local;
                    dto.merge(geo.getDistanceAndDuration(source, local, TravelMethod.DRIVING));
                    if (dto.distance > 0) {
                        dtoList.add(dto);
                    }
                }
                if (locations.size() > 0) {
                    if (locations.size() > 1) {
                        Collections.sort(dtoList, Comparator.comparing(GeoRefServiceDTO::getDistance).
                                thenComparing(GeoRefServiceDTO::getName));
                        Set<String> retain = new HashSet<>();
                        retain.add(dtoList.get(0).getName());
                        locations.retainAll(retain);
                    }
                    //CHAMAR AQUI A GRAMATICA, apenas mandando o primeiro objeto, porque é o mais
                    //return metodoDaGramatica(riskFactor, distancia,"distancia");
                    //o return type pode ser alterado conforme o método, desde que retorne algo referente a Low, Medium e High
                    return new Designation("MEDIUM");
                }

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            }
            return new Designation("(não detetado)");
        }
    },
    TEMPO {
        @Override
        public String getMetrics() {
            return "Tempo";
        }

        @Override
        public String toString() {
            return getMetrics();
        }

        @Override
        public Designation getClassification(String source, List<String> locations, RiskFactor risk) {
            try {
                GeoReferenceUtil geo = new GeoReferenceUtil();

                if (locations.isEmpty()) {
                    locations.addAll(geo.getSurroundingsAsString(source, risk.obtainSTName()));
                }

                List<GeoRefServiceDTO> dtoList = new ArrayList<>();
                for (String local : locations) {
                    GeoRefServiceDTO dto = new GeoRefServiceDTO();
                    dto.name = local;
                    dto.merge(geo.getDistanceAndDuration(source, local, TravelMethod.DRIVING));
                    if (dto.duration > 0) {
                        dtoList.add(dto);
                    }
                }
                if (locations.size() > 0) {
                    if (locations.size() > 1) {
                        Collections.sort(dtoList, Comparator.comparing(GeoRefServiceDTO::getDuration).
                                thenComparing(GeoRefServiceDTO::getName));
                        Set<String> retain = new HashSet<>();
                        retain.add(dtoList.get(0).getName());
                        locations.retainAll(retain);
                    }
                    //CHAMAR AQUI A GRAMATICA, apenas mandando o primeiro objeto, porque é o mais
                    //return metodoDaGramatica(riskFactor, duration,"duration");
                    //o return type pode ser alterado conforme o método, desde que retorne algo referente a Low, Medium e High
                    return new Designation("LOW");
                }

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            }
            return new Designation("(não detetado)");
        }
    },
    QUANTIDADE {
        @Override
        public String getMetrics() {
            return "Quantidade";
        }

        @Override
        public String toString() {
            return getMetrics();
        }

        @Override
        public Designation getClassification(String source, List<String> locations, RiskFactor risk) {
            try {
                GeoReferenceUtil geo = new GeoReferenceUtil();

                if (locations.isEmpty()) {
                    locations.addAll(geo.getSurroundingsAsString(source, risk.obtainSTName()));
                }

                //CHAMAR AQUI A GRAMATICA
                //return metodoDaGramatica(riskFactor, locations.size(),"quantidade");
                //o return type pode ser alterado conforme o método, desde que retorne algo referente a Low, Medium e High
                return new Designation("HIGH");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
            return new Designation("(não detetado)");
        }

    }
}
