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
@Table(name = "outportvariable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Outportvariable.findAll", query = "SELECT o FROM Outportvariable o"),
    @NamedQuery(name = "Outportvariable.findByIdOutportVariable", query = "SELECT o FROM Outportvariable o WHERE o.idOutportVariable = :idOutportVariable"),
    @NamedQuery(name = "Outportvariable.findByNameOutport", query = "SELECT o FROM Outportvariable o WHERE o.nameOutport = :nameOutport"),
    @NamedQuery(name = "Outportvariable.findByDataTypeOutport", query = "SELECT o FROM Outportvariable o WHERE o.dataTypeOutport = :dataTypeOutport"),
    @NamedQuery(name = "Outportvariable.findByPortDimensionOutport", query = "SELECT o FROM Outportvariable o WHERE o.portDimensionOutport = :portDimensionOutport"),
    @NamedQuery(name = "Outportvariable.findByIdSimulink", query = "SELECT i FROM Outportvariable i WHERE i.simulinkModelidModel.idModel = :idModel")})


public class Outportvariable implements Serializable {

    public static final String FIND_ALL = "Outportvariable.findAll";
    public static final String FIND_BY_ID = "Outportvariable.findByIdOutportVariable";
     public static final String FIND_BY_IDSIM = "Outportvariable.findByIdSimulink";
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOutportVariable")
    private Integer idOutportVariable;
    @Size(max = 45)
    @Column(name = "nameOutport")
    private String nameOutport;
    @Size(max = 45)
    @Column(name = "dataTypeOutport")
    private String dataTypeOutport;
    @Size(max = 45)
    @Column(name = "portDimensionOutport")
    private String portDimensionOutport;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL , mappedBy = "outportVariableidOutportVariable")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Collection<Result> resultCollection;
	 
    @JoinColumn(name = "SimulinkModel_idModel" , referencedColumnName = "idModel")  //  updatable = false, insertable = false,
    @ManyToOne
    @JsonBackReference //(optional = false)
    private Simulinkmodel simulinkModelidModel ;

    public Outportvariable() {
    }

    public Outportvariable(Integer idOutportVariable) {
        this.idOutportVariable = idOutportVariable;
    }

    public Integer getIdOutportVariable() {
        return idOutportVariable;
    }

    public void setIdOutportVariable(Integer idOutportVariable) {
        this.idOutportVariable = idOutportVariable;
    }

    public String getNameOutport() {
        return nameOutport;
    }

    public void setNameOutport(String nameOutport) {
        this.nameOutport = nameOutport;
    }

    public String getDataTypeOutport() {
        return dataTypeOutport;
    }

    public void setDataTypeOutport(String dataTypeOutport) {
        this.dataTypeOutport = dataTypeOutport;
    }

    public String getPortDimensionOutport() {
        return portDimensionOutport;
    }

    public void setPortDimensionOutport(String portDimensionOutport) {
        this.portDimensionOutport = portDimensionOutport;
    }

    @XmlTransient
    public Collection<Result> getResultCollection() {
        return resultCollection;
    }

    public void setResultCollection(Collection<Result> resultCollection) {
        this.resultCollection = resultCollection;
    }

    public Simulinkmodel getSimulinkModelidModel() {
        return simulinkModelidModel;
    }

    public void setSimulinkModelidModel(Simulinkmodel simulinkModelidModel) {
        this.simulinkModelidModel = simulinkModelidModel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOutportVariable != null ? idOutportVariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Outportvariable)) {
            return false;
        }
        Outportvariable other = (Outportvariable) object;
        if ((this.idOutportVariable == null && other.idOutportVariable != null) || (this.idOutportVariable != null && !this.idOutportVariable.equals(other.idOutportVariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.telnet.modelbasedtesting.entities.Outportvariable[ idOutportVariable=" + idOutportVariable + " ]";
    }
    
}
