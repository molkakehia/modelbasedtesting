/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanen LAFFET
 */
@Entity
@Table(name = "testcase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testcase.findAll", query = "SELECT t FROM Testcase t"),
    @NamedQuery(name = "Testcase.findByIdTestCase", query = "SELECT t FROM Testcase t WHERE t.idTestCase = :idTestCase"),
    @NamedQuery(name = "Testcase.findByNumeroTestCase", query = "SELECT t FROM Testcase t WHERE t.numeroTestCase = :numeroTestCase"),
    @NamedQuery(name = "Testcase.findByValueTestCase", query = "SELECT t FROM Testcase t WHERE t.valueTestCase = :valueTestCase")})
    
public class Testcase implements Serializable {
public static final String FIND_ALL = "Testcase.findAll";
    public static final String FIND_BY_ID = "Testcase.findByIdTestCase";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTestCase")
    private Integer idTestCase;
    @Size(max = 45)
    @Column(name = "numeroTestCase")
    private String numeroTestCase;
    @Size(max = 45)
    @Column(name = "valueTestCase")
    private String valueTestCase;
    @JoinColumn(name = "inportvariable_idInportVariable" ,  referencedColumnName = "idInportVariable")  //  updatable = false, insertable = false) //,
    @ManyToOne //(optional = false)
    @JsonBackReference
    private Inportvariable inportvariableidInportVariable;

    public Testcase() {
    }

    public Testcase(Integer idTestCase) {
        this.idTestCase = idTestCase;
    }

    public Integer getIdTestCase() {
        return idTestCase;
    }

    public void setIdTestCase(Integer idTestCase) {
        this.idTestCase = idTestCase;
    }

    public String getNumeroTestCase() {
        return numeroTestCase;
    }

    public void setNumeroTestCase(String numeroTestCase) {
        this.numeroTestCase = numeroTestCase;
    }

    public String getValueTestCase() {
        return valueTestCase;
    }

    public void setValueTestCase(String valueTestCase) {
        this.valueTestCase = valueTestCase;
    }

    public Inportvariable getInportvariableidInportVariable() {
        return inportvariableidInportVariable;
    }

    public void setInportvariableidInportVariable(Inportvariable inportvariableidInportVariable) {
        this.inportvariableidInportVariable = inportvariableidInportVariable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTestCase != null ? idTestCase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testcase)) {
            return false;
        }
        Testcase other = (Testcase) object;
        if ((this.idTestCase == null && other.idTestCase != null) || (this.idTestCase != null && !this.idTestCase.equals(other.idTestCase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.telnet.modelbasedtesting.entities.Testcase[ idTestCase=" + idTestCase + " ]";
    }
    
}
