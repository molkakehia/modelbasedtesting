/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.entities;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Hanen LAFFET
 */
@Entity
@Table(name = "simulinkmodel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Simulinkmodel.findAll", query = "SELECT s FROM Simulinkmodel s"),
    @NamedQuery(name = "Simulinkmodel.findByIdModel", query = "SELECT s FROM Simulinkmodel s WHERE s.idModel = :idModel"),
    @NamedQuery(name = "Simulinkmodel.findByNameModel", query = "SELECT s FROM Simulinkmodel s WHERE s.nameModel = :nameModel"),
    @NamedQuery(name = "Simulinkmodel.findByDescriptionModel", query = "SELECT s FROM Simulinkmodel s WHERE s.descriptionModel = :descriptionModel"),
    @NamedQuery(name = "Simulinkmodel.findByVersionModel", query = "SELECT s FROM Simulinkmodel s WHERE s.versionModel = :versionModel")})
@NamedQuery(name= "Simulinkmodel.findByNamedModel1" , query= "SELECT s FROM Simulinkmodel s WHERE s.nameModel = :nameModel")
public class Simulinkmodel implements Serializable {
    
    public static final String FIND_ALL = "Simulinkmodel.findAll";
    public static final String FIND_BY_ID = "Simulinkmodel.findByIdModel";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idModel")
    private Integer idModel;
    @Size(max = 45)
    @Column(name = "nameModel")
    private String nameModel;
    @Size(max = 45)
    @Column(name = "descriptionModel")
    private String descriptionModel;
    @Size(max = 45)
    @Column(name = "versionModel")
    private String versionModel;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "simulinkModelidModel")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Collection<Inportvariable> inportvariableCollection;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "simulinkModelidModel")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Collection<Outportvariable> outportvariableCollection;

    public Simulinkmodel() {
    }

    public Simulinkmodel(Integer idModel) {
        this.idModel = idModel;
    }

    public Integer getIdModel() {
        return idModel;
    }

    public void setIdModel(Integer idModel) {
        this.idModel = idModel;
    }

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public String getDescriptionModel() {
        return descriptionModel;
    }

    public void setDescriptionModel(String descriptionModel) {
        this.descriptionModel = descriptionModel;
    }

    public String getVersionModel() {
        return versionModel;
    }

    public void setVersionModel(String versionModel) {
        this.versionModel = versionModel;
    }

    @XmlTransient
    public Collection<Inportvariable> getInportvariableCollection() {
        return inportvariableCollection;
    }

    public void setInportvariableCollection(Collection<Inportvariable> inportvariableCollection) {
        this.inportvariableCollection = inportvariableCollection;
    }

    @XmlTransient
    public Collection<Outportvariable> getOutportvariableCollection() {
        return outportvariableCollection;
    }

    public void setOutportvariableCollection(Collection<Outportvariable> outportvariableCollection) {
        this.outportvariableCollection = outportvariableCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModel != null ? idModel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Simulinkmodel)) {
            return false;
        }
        Simulinkmodel other = (Simulinkmodel) object;
        if ((this.idModel == null && other.idModel != null) || (this.idModel != null && !this.idModel.equals(other.idModel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.telnet.modelbasedtesting.entities.Simulinkmodel[ idModel=" + idModel + " ]";
    }
    
}
