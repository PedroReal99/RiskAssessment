/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import eapli.framework.infrastructure.repositories.Repository;

/**
 *
 * @author morei
 */
public interface CoverageRepository extends Repository<Coverage, CoverageName>{

    /**
     * Returns the coverage with the name
     *
     * @param name
     * @return
     */
    public Coverage findByName(CoverageName name);
}
