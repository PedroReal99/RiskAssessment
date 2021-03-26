/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs;

import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.Coverage.Coverage;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import eapli.framework.domain.model.DomainFactory;
import java.util.ArrayList;
import java.util.List;
import core.utils.Matrix;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author CarloS
 */
public class MatrixBuilder implements DomainFactory<RiskMatrix> {

    Matrix<Coverage, RiskFactor, ColumnBuilder> matrix;

    public static enum State {
        BASE, DETAILED, DEFINED
    }

    private MatrixState status;

    private MatrixVersion version;

    /**
     * Constructor of Matrix using its state as parameter
     *
     * @param state
     */
    public MatrixBuilder(State state) {

        switch (state) {
            case DETAILED:
                this.status = MatrixState.DETAILED;
                break;
            case DEFINED:
                this.status = MatrixState.DEFINED;
                break;
            default:
                this.status = MatrixState.BASE;
                break;
        }
        this.version = MatrixVersion.tempVersion();
        matrix = new Matrix<>();
    }

    /**
     * Constructor of Matrix using its state and version as parameters
     *
     * @param state
     * @param version
     */
    public MatrixBuilder(State state, MatrixVersion version) {
        this.version = version;
        switch (state) {
            case DETAILED:
                this.status = MatrixState.DETAILED;
                break;
            case DEFINED:
                this.status = MatrixState.DEFINED;
                break;
            default:
                this.status = MatrixState.BASE;
                break;
        }
        matrix = new Matrix<>();
    }

    /**
     * Constructor of Matrix using a existing one
     *
     * @param rMatrix
     */
    public MatrixBuilder(RiskMatrix rMatrix) {
        this.version = MatrixVersion.create(rMatrix.version.toString() + "_edit");

        if (rMatrix.status.equals(MatrixState.BASE)) {
            this.status = MatrixState.BASE;
        } else if (rMatrix.status.equals(MatrixState.DEFINED)) {
            this.status = MatrixState.DEFINED;
        } else {
            this.status = MatrixState.DETAILED;
        }

        matrix = new Matrix<>();

        for (Line line : rMatrix.obtainMatrix()) {
            for (BaseColumn bc : line.obtainColumns().values()) {
                addColumnBuilder(line.obtainCoverage(), new DetailedColumnBuilder(bc));
            }
        }
    }

    /**
     * Changes the state of the matrix
     *
     * @param state
     */
    public void changeState(State state) {
        switch (state) {
            case DETAILED:
                this.status = MatrixState.DETAILED;
                break;
            case DEFINED:
                this.status = MatrixState.DEFINED;
                break;
            default:
                this.status = MatrixState.BASE;
                break;
        }
    }

    /**
     * Adds a new ColumnBuilder, for one Coverage and RiskFactor pair
     *
     * @param cov
     * @param b
     * @return
     */
    public final ColumnBuilder addColumnBuilder(Coverage cov, ColumnBuilder b) {
        return matrix.set(cov, b.getRisk(), b);
    }

    public final ColumnBuilder getColumnBuilder(Coverage cov, RiskFactor risk) {
        return matrix.get(cov, risk);
    }

    /**
     * Validates the matrix
     *
     * @return true if there are no missing elements and all the elemnts are
     * valid
     */
    public boolean validate() {
        Set<Coverage> lines = matrix.getLines();

        List<ColumnBuilder> clb;
        List<RiskFactor> risks;
        for (Coverage cov : lines) {
            clb = matrix.getLine(cov);
            risks = cov.getAssociatedRiskFactors();

            if (risks.size() != clb.size()) {
                System.out.println("Wrong size on cov " + risks.size() + " " + clb.size());
                return false;
            }

            for (ColumnBuilder cb : clb) {

                if (!risks.contains(cb.getRisk())) {
                    System.out.println("does not contain " + cb.getRisk());

                    return false;
                }

                if (!validateColumn(cb)) {
                    System.out.println("invalid column");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Validates the column depending on what it is needed to fill the matrix in
     * its current state
     *
     * @param column
     * @return
     */
    public boolean validateColumn(ColumnBuilder column) {
        if (status == MatrixState.BASE) {
            return new ColumnBuilder(column).validate();
        }
        if (status == MatrixState.DEFINED) {

            return new DefinedColumnBuilder(column).validate();
        }

        return new DetailedColumnBuilder (column).validate();
    }

    /**
     * Fills the missing elements of the matrix with default ones
     *
     * @return true if and only if all the columnbuilders are valid
     */
    public boolean fillByDefault() {
        if (status == MatrixState.BASE) {
            filler(ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE));
        }
        if (status == MatrixState.DEFINED) {
            filler(ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED));
        }
        filler(ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED));
        return true;
    }

    private void filler(ColumnBuilder c) {
        for (Coverage coverage : matrix.getLines()) {
            for (RiskFactor risk : coverage.getAssociatedRiskFactors()) {
                if (matrix.get(coverage, risk) == null) {
                    matrix.set(coverage, risk, c.defaultBuilder(risk));
                }
            }
        }
    }

    /**
     * Returns the current state of the matrix
     *
     * @return
     */
    public State getState() {
        if (status.equals(MatrixState.BASE)) {
            return State.BASE;
        } else if (status.equals(MatrixState.DEFINED)) {
            return State.DEFINED;
        } else {
            return State.DETAILED;
        }
    }

    public MatrixVersion getVersion() {
        return this.version;
    }

    /**
     * Builds a new matrix if possible
     *
     * @return the built matrix, null if the matrix does not return true on
     * validate()
     */
    @Override
    public RiskMatrix build() {
        if (!validate()) {
            return null;
        }
        if (version == null || version.isClone()) {
            version = MatrixVersion.tempVersion();
        }
        List<Line> lines = new ArrayList<>();
        fillLine(lines);
        return new RiskMatrix(version, status, lines);
    }

    private void fillLine(List<Line> lines) {
        for (Coverage coverage : matrix.getLines()) {
            lines.add(new Line(getColumns(coverage), coverage));
        }
    }

    private Map<RiskFactor, BaseColumn> getColumns(Coverage coverage) {
        Map<RiskFactor, BaseColumn> map = new HashMap<>();
        for (ColumnBuilder cb : matrix.getLine(coverage)) {
            map.put(cb.getRisk(), cb.build());
        }
        return map;
    }
}
