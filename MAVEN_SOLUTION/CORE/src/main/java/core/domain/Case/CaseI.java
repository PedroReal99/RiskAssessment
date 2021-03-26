/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import core.domain.Insurance.InsuranceObject;
import core.domain.RiskMatrixs.RiskMatrix;
import core.domain.User.Email;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;

/**
 *
 * @author morei
 */
@Entity
public class CaseI implements AggregateRoot<CaseCode>, Serializable {

    @EmbeddedId
    private CaseCode code;
    @Embedded
    private CaseType type;
    @Embedded
    private CaseState caseState;
    @Embedded
    private CasePriority priority;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "date", column = @Column(name = "creation_date"))
    })
    private CaseDate creationDate;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "date", column = @Column(name = "last_update"))
    })
    private CaseDate lastUpdate;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "date", column = @Column(name = "otherdate"))
    })
    private CaseDate finalDate;

    @Column
    private boolean needsValidation;

    @ManyToMany(cascade = {
        CascadeType.ALL
    })
    @JoinColumns({
        @JoinColumn(name = "name", referencedColumnName = "name")
    })
    private List<InsuranceObject> insurances = new ArrayList<>();

    @Embedded
    private Email employee;

    @Embedded
    private Comment comment;

    @Embedded
    private History history;

    private int fieldID;

    protected CaseI() {
        //Persistence JPA
    }

    public CaseI(CaseType type, CaseState state, CaseCode code, boolean needsValidation, CasePriority priority, CaseDate date) {
        this.type = type;
        this.caseState = state;
        this.code = code;
        this.fieldID = hashCode();
        this.insurances = new ArrayList<>();
        this.needsValidation = needsValidation;
        this.priority = priority;
        this.creationDate = date;
        this.comment = new Comment("");
        history = new History();
        history.addEvent("", History.CREATED, "");
    }

    public CaseI(CaseType type, CaseState state, CaseCode code, List<InsuranceObject> insurances, boolean needsValidation, CasePriority priority, CaseDate date) {
        this.type = type;
        this.caseState = state;
        this.code = code;
        this.fieldID = hashCode();
        this.insurances = insurances;
        this.needsValidation = needsValidation;
        this.priority = priority;
        this.creationDate = date;
        this.comment = new Comment("");
        history = new History();
        history.addEvent("", History.CREATED, "");
    }

    public CaseI(CaseType type, CaseState state, CaseCode code, List<InsuranceObject> insurances, boolean needsValidation, CasePriority priority, CaseDate date, Email employes) {
        this.type = type;
        this.caseState = state;
        this.code = code;
        this.fieldID = hashCode();
        this.insurances = insurances;
        this.needsValidation = needsValidation;
        this.priority = priority;
        this.creationDate = date;
        this.comment = new Comment("");
        history = new History();
        history.addEvent("", History.CREATED, "");
    }

    public void associateInsurance(String user, InsuranceObject insuranceObject) {
        insurances.add(insuranceObject);
        insuranceObject.setCase(this);
        history.addEvent(user, History.MODIFICATION, "added insurance " + insuranceObject.obtainName());
        updateLastUpdate();

    }

    private final void fillInsurances(List<InsuranceObject> insurances) {
        for (InsuranceObject io : insurances) {
            this.insurances.add(io);
            io.setCase(this);
        }
    }

    public void associateEmployee(String user, Email employes) {
        Preconditions.nonNull(employes);
        Preconditions.ensure(this.employee == null);
        this.lastUpdate = CaseDate.now();
        this.employee = employes;
        this.history.addEvent(user, History.ASSIGNING, " user " + employes);
        updateLastUpdate();
    }

    public void validationStartDate(CaseDate caseDate) {
        this.lastUpdate = caseDate;
    }

    public void validatedDate(CaseDate caseDate) {
        this.caseState=CaseState.PROCESSED;
        this.finalDate = caseDate;
    }

    public List<InsuranceObject> getAssociatedInsuranceObjects() {
        return new ArrayList<>(insurances);
    }

    @Override
    public String toString() {
        return "Type: " + getType().toString() + "State: + " + getState().toString() + "Code: " + code.toString() + " Date:" + creationDate.toString();
    }

    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.getType());
        hash = 83 * hash + Objects.hashCode(this.getState());
        hash = 83 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CaseI other = (CaseI) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.caseState, other.caseState)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        return Objects.equals(this.code, other.code);
    }

    public void addComment(String user, String comment) {
        this.comment.addComment(comment);
        this.history.addEvent(user, History.MODIFICATION, comment);
        updateLastUpdate();
    }

    public String getComment() {
        return this.comment.toString();
    }

    /**
     * Adiciona uma modificação não tracejada ao historico, estas são
     * modificações que não são rastreadas pelos métodos de CaseI
     *
     * @param user
     * @param comment
     */
    public void addUntrackedModification(String user, String comment) {
        this.history.addEvent(user, History.MODIFICATION, comment);
        updateLastUpdate();
    }

    public CaseDate obtainAssignedDate() {
        return lastUpdate;
    }

    public CaseDate obtainValidatedDate() {
        return finalDate;
    }

    public String obtainType() {
        return getType().toString();
    }

    public String obtainState() {
        return getState().toString();
    }

    public String obtainCode() {
        return code.toString();
    }

    public boolean needsValidation() {
        return needsValidation;
    }

    public String priority() {
        return priority.toString();
    }

    public String obtainDate() {
        return creationDate.toString();
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public CaseCode identity() {
        return code;
    }

    /**
     * @return the type
     */
    protected CaseType getType() {
        return type;
    }

    /**
     * @return the state
     */
    protected CaseState getState() {
        return caseState;
    }

    protected CaseDate getDate() {
        return creationDate;
    }

    /**
     * @return the type
     */
    public CaseType obtainTypeObject() {
        return getType();
    }

    /**
     * @return the state
     */
    public CaseState obtainStateObject() {
        return getState();
    }

    public Email getEmployee() {
        return employee;
    }

    public boolean calculateRiskAssessment(String user, RiskMatrix rm, boolean det) {
        for (InsuranceObject is : this.getAssociatedInsuranceObjects()) {
            is.calculateFinalScore(rm, det);
        }
        this.history.addEvent(user, History.EVALUATION, "");
        return true;
    }

    public void alterCaseStatus(String user, CaseState st) {
        this.caseState = st;
        if (st.equals(CaseState.PROCESSED)) {
            setFinalDate();
        }
        updateLastUpdate();
        this.history.addEvent(user, History.MODIFICATION, "state updated to " + st);
    }

    public List<String> history() {
        return this.history.list();
    }

    public void updateLastUpdate() {
        this.lastUpdate = CaseDate.now();
    }

    public void setFinalDate() {
        this.finalDate = CaseDate.now();
    }

    public void alterDate(String date) {
        this.lastUpdate = new CaseDate(date);
    }

    public String obtainLastUpdatedDate() {
        return lastUpdate.toString();
    }

}
