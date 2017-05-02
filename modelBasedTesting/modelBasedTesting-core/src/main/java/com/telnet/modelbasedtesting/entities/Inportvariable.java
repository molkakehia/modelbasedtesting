/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Hanen LAFFET
 */
@Entity
@Table(name = "inportvariable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inportvariable.findAll", query = "SELECT i FROM Inportvariable i"),
    @NamedQuery(name = "Inportvariable.findByIdInportVariable", query = "SELECT i FROM Inportvariable i WHERE i.idInportVariable = :idInportVariable"),
    @NamedQuery(name = "Inportvariable.findByNameInport", query = "SELECT i FROM Inportvariable i WHERE i.nameInport = :nameInport"),
    @NamedQuery(name = "Inportvariable.findByDataTypeInport", query = "SELECT i FROM Inportvariable i WHERE i.dataTypeInport = :dataTypeInport"),
    @NamedQuery(name = "Inportvariable.findByPortDimensionInport", query = "SELECT i FROM Inportvariable i WHERE i.portDimensionInport = :portDimensionInport"),
    @NamedQuery(name = "Inportvariable.findByIdSimulink", query = "SELECT i FROM Inportvariable i WHERE i.simulinkModelidModel.idModel = :idModel")})


public class Inportvariable implements Serializable {

    public static final String FIND_ALL = "Inportvariable.findAll";
    public static final String FIND_BY_ID = "Inportvariable.findByIdInportVariable";
    public static final String FIND_BY_IDSIM = "Inportvariable.findByIdSimulink";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInportVariable")
    private Integer idInportVariable;
    @Size(max = 45)
    @Column(name = "nameInport")
    private String nameInport;
    @Size(max = 45)
    @Column(name = "dataTypeInport")
    private String dataTypeInport;
    @Size(max = 45)
    @Column(name = "PortDimensionInport")
    private String portDimensionInport;
    
    @JoinColumn(name = "SimulinkModel_idModel",    referencedColumnName = "idModel")// updatable = false, insertable = false ) //
    @ManyToOne //(optional = false)
    @JsonBackReference
    private Simulinkmodel simulinkModelidModel;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "inportvariableidInportVariable")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Collection<Testcase> testcaseCollection;

    public Inportvariable() {
    }

    public Inportvariable(Integer idInportVariable) {
        this.idInportVariable = idInportVariable;
    }

    public Integer getIdInportVariable() {
        return idInportVariable;
    }

    public void setIdInportVariable(Integer idInportVariable) {
        this.idInportVariable = idInportVariable;
    }

    public String getNameInport() {
        return nameInport;
    }

    public void setNameInport(String nameInport) {
        this.nameInport = nameInport;
    }

    public String getDataTypeInport() {
        return dataTypeInport;
    }

    public void setDataTypeInport(String dataTypeInport) {
        this.dataTypeInport = dataTypeInport;
    }

    public String getPortDimensionInport() {
        return portDimensionInport;
    }

    public void setPortDimensionInport(String portDimensionInport) {
        this.portDimensionInport = portDimensionInport;
    }

    public Simulinkmodel getSimulinkModelidModel() {
        return simulinkModelidModel;
    }

    public void setSimulinkModelidModel(Simulinkmodel simulinkModelidModel) {
        this.simulinkModelidModel = simulinkModelidModel;
    }

    @XmlTransient
    public Collection<Testcase> getTestcaseCollection() {
        return testcaseCollection;
    }

    public void setTestcaseCollection(Collection<Testcase> testcaseCollection) {
        this.testcaseCollection = testcaseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInportVariable != null ? idInportVariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inportvariable)) {
            return false;
        }
        Inportvariable other = (Inportvariable) object;
        if ((this.idInportVariable == null && other.idInportVariable != null) || (this.idInportVariable != null && !this.idInportVariable.equals(other.idInportVariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.telnet.modelbasedtesting.entities.Inportvariable[ idInportVariable=" + idInportVariable + " ]";
    }
    
}
