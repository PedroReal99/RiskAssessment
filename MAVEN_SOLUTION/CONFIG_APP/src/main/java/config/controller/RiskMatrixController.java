package config.controller;

import core.domain.Coverage.Coverage;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.RiskMatrix;
import core.domain.RiskMatrixs.Attributes.Weight;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import core.domain.RiskMatrixs.MatrixState;
import core.domain.RiskMatrixs.MatrixVersion;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import eapli.framework.application.Controller;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class RiskMatrixController implements Controller {

    private MatrixRepository rep = PersistenceContext.repositories().riskMatrixRepository();
    private MatrixBuilder builder;

    public RiskMatrixController() {
        builder = new MatrixBuilder(MatrixBuilder.State.BASE);
    }

    public String state() {
        return builder.getState().name();
    }

    public String version() {
        return builder.getVersion().toString();
    }

    /**
     * Return a list of versions and states
     *
     * @return
     */
    public List<Pair<MatrixVersion, MatrixState>> getAll() {
        List<Pair<MatrixVersion, MatrixState>> list = new ArrayList<>();
        for (RiskMatrix rm : rep.findAll()) {
            list.add(new Pair(rm.getVersion(), rm.getState()));
        }
        return list;
    }

    /**
     * Opens a previousMatrix
     *
     * @param name
     * @return
     */
    public RiskMatrix getMatrix(String name) {
        RiskMatrix matrix = rep.findByName(name);
        System.out.println("Matriz selecionada: "+matrix);
        if (matrix != null) {
            builder = new MatrixBuilder(matrix);
        }
        return matrix;
    }

    /**
     * Opens a CurrentMatrix
     *
     * @param name
     * @return
     */
    public RiskMatrix getCurrentMatrix() {
        return builder.build();
    }

    /**
     * Starts a new matrix characterization from another one, as long as its
     * state is at leas Defined
     *
     * @param name
     * @return
     */
    public boolean startFromMatrix(String name) {
        try {
            RiskMatrix matrix = rep.findByName(name);
            if (matrix != null) {
                builder = RiskMatrix.startFromMatrix(matrix);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Creates a new Matrix
     */
    public void newMatrix() {
        builder = new MatrixBuilder(MatrixBuilder.State.BASE);
    }

    /**
     * Forces a change of state
     *
     * @param state
     */
    public void changeState(MatrixBuilder.State state) {
        builder.changeState(state);
    }

    /**
     * Adds a basic column to the matrix
     *
     * @param cov
     * @param riskFactor
     * @return
     */
    public boolean addBasicColumn(Coverage cov, RiskFactor riskFactor) {
        ColumnBuilder cb;
        if ((cb = builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
            cb.setRiskFactor(riskFactor);
            builder.addColumnBuilder(cov, cb);
        }
        return builder.validateColumn(cb);
    }

    /**
     * Adds an element, weight to the matrix,changing its state to defined if
     * previously base
     *
     * @param cov Coverage (line)
     * @param riskFactor risk factor (column)
     * @param weight element to add
     * @return true if the column has all the information it requires
     */
    public boolean addElement(Coverage cov, RiskFactor riskFactor, Weight weight) {
        if (builder.getState() == MatrixBuilder.State.BASE) {
            builder.changeState(MatrixBuilder.State.DEFINED);
        }
        DefinedColumnBuilder cb;
        if ((cb = (DefinedColumnBuilder) builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
            cb.setRiskFactor(riskFactor);
        }
        cb = new DefinedColumnBuilder(cb);
        cb.setWeight(weight);
        builder.addColumnBuilder(cov, cb);
        return builder.validateColumn(cb);
    }

    /**
     * Adds an element, contribution to the matrix,changing its state to defined
     * if previously base
     *
     * @param cov Coverage (line)
     * @param riskFactor risk factor (column)
     * @param contribution element to add
     * @return true if the column has all the information it requires
     */
    public boolean addElement(Coverage cov, RiskFactor riskFactor, Contribution contribution) {
        if (builder.getState() == MatrixBuilder.State.BASE) {
            builder.changeState(MatrixBuilder.State.DEFINED);
        }
        DefinedColumnBuilder cb;
        if ((cb = (DefinedColumnBuilder) builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
            cb.setRiskFactor(riskFactor);
        }
        cb = new DefinedColumnBuilder(cb);
        cb.setContribution(contribution);

        builder.addColumnBuilder(cov, cb);
        return builder.validateColumn(cb);
    }

    /**
     * Adds an element, need to the matrix,changing its state to defined if
     * previously base
     *
     * @param cov Coverage (line)
     * @param riskFactor risk factor (column)
     * @param need element to add
     * @return true if the column has all the information it requires
     */
    public boolean addElement(Coverage cov, RiskFactor riskFactor, NeedOfAnalisys need) {
        if (builder.getState() == MatrixBuilder.State.BASE) {
            builder.changeState(MatrixBuilder.State.DEFINED);
        }
        DefinedColumnBuilder cb;
        if ((cb = (DefinedColumnBuilder) builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
            cb.setRiskFactor(riskFactor);
        }
        cb = new DefinedColumnBuilder(cb);
        cb.setNeed(need);

        builder.addColumnBuilder(cov, cb);
        return builder.validateColumn(cb);
    }

    /**
     * Adds an scale, need to the matrix,changing its state to detailed if
     * previously not
     *
     * @param cov Coverage (line)
     * @param riskFactor risk factor (column)
     * @param scale element to add
     * @return true if the column has all the information it requires
     */
    public boolean addElement(Coverage cov, RiskFactor riskFactor, Scale scale) {
        if (builder.getState() != MatrixBuilder.State.DETAILED) {
            builder.changeState(MatrixBuilder.State.DETAILED);
        }
        DetailedColumnBuilder cb;
        if ((cb = (DetailedColumnBuilder) builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = (DetailedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED);
            cb.setRiskFactor(riskFactor);
        }
        cb = new DetailedColumnBuilder(cb);
        cb.setScale(scale);

        builder.addColumnBuilder(cov, cb);
        return builder.validateColumn(cb);
    }

    /**
     * Adds a defined column to the matrix changing its state to defined if
     * previously base
     *
     * @param cov line
     * @param riskFactor column
     * @param weight weight
     * @param contribution contribution
     * @param necessity necessity
     * @return true if the column has all the information it requires
     */
    public boolean addDefinedColumn(Coverage cov, RiskFactor riskFactor, Weight weight, Contribution contribution, NeedOfAnalisys necessity) {
        if (builder.getState() == MatrixBuilder.State.BASE) {
            builder.changeState(MatrixBuilder.State.DEFINED);
        }

        DefinedColumnBuilder cb;
        if ((cb = (DefinedColumnBuilder) builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
            cb.setRiskFactor(riskFactor);
        }
        cb = new DefinedColumnBuilder(cb);
        cb.setNeed(necessity);
        cb.setContribution(contribution);
        cb.setWeight(weight);

        builder.addColumnBuilder(cov, cb);
        return builder.validateColumn(cb);
    }

    /**
     * Adds a defined column to the matrix changing its state to defined if
     * previously not
     *
     * @param cov line
     * @param riskFactor column
     * @param weight weight
     * @param contribution contribution
     * @param necessity necessity
     * @param scale scale
     * @return true if the column has all the information it requires
     */
    public boolean addDetailedColumn(Coverage cov, RiskFactor riskFactor, Weight weight, Contribution contribution, NeedOfAnalisys necessity, Scale scale) {

        if (builder.getState() != MatrixBuilder.State.DETAILED) {
            builder.changeState(MatrixBuilder.State.DETAILED);
        }

        DetailedColumnBuilder cb;
        if ((cb = (DetailedColumnBuilder) builder.getColumnBuilder(cov, riskFactor)) == null) {
            cb = (DetailedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED);
            cb.setRiskFactor(riskFactor);
        }
        cb = new DetailedColumnBuilder(cb);
        cb.setNeed(necessity);
        cb.setContribution(contribution);
        cb.setWeight(weight);
        cb.setScale(scale);

        builder.addColumnBuilder(cov, cb);
        return builder.validateColumn(cb);
    }

    /**
     * Persists and returns the createdMatrix
     *
     * @return
     */
    public RiskMatrix createMatrix() {
        RiskMatrix mat = builder.build();
        if (mat != null) {
            rep.save(builder.build());
        }
        return mat;
    }

    /**
     * Published a matrix with its desired date
     *
     * @param version version of the matrix
     * @param date date from when the matrix will be valid
     * @return
     */
    public boolean publishMatrix(String version, String date) {
        RiskMatrix mat = rep.findByName(version);
        rep.save(mat);
        if (mat == null) {
            return false;
        }
        if (!mat.publish(date)) {
            System.out.println("Error publishing!");
            return false;
        }
        System.out.println("HEY NOW");
        rep.save(mat);
        updateInForce();
        return true;
    }

    /**
     * Checks if there is a new matrix to be in forced updates it if there is.
     *
     * @return true if a new matrix was in forced
     */
    public boolean updateInForce() {
        List<RiskMatrix> list = rep.findByState(MatrixState.PUBLISHED);
        RiskMatrix rm = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).canBeInForce()) {
                rm = list.get(i);
                break;
            }
        }

        if (rm == null) {
            return false;
        }

        RiskMatrix oldMatrix = rep.getInForceMatrix();
        if (oldMatrix != null) {
            oldMatrix.turnObsolete();
            rep.save(oldMatrix);
        }
        System.out.println("TURNING IN FORCE");
        rm.turnInForce();
        rep.save(rm);
        return true;
    }
}
