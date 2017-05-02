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
@Table(name = "result")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Result.findAll", query = "SELECT r FROM Result r"),
    @NamedQuery(name = "Result.findByIdResult", query = "SELECT r FROM Result r WHERE r.idResult = :idResult"),
    @NamedQuery(name = "Result.findByNumeroResult", query = "SELECT r FROM Result r WHERE r.numeroResult = :numeroResult"),
    @NamedQuery(name = "Result.findByExpectedOutput", query = "SELECT r FROM Result r WHERE r.expectedOutput = :expectedOutput"),
    @NamedQuery(name = "Result.findByObtainedOutput", query = "SELECT r FROM Result r WHERE r.obtainedOutput = :obtainedOutput")})
public class Result implements Serializable {

    public static final String FIND_ALL = "Result.findAll";
    public static final String FIND_BY_ID = "Result.findByIdResult";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idResult")
    private Integer idResult;
    @Size(max = 45)
    @Column(name = "numeroResult")
    private String numeroResult;
    @Size(max = 255)
    @Column(name = "expectedOutput")
    private String expectedOutput;
    @Size(max = 45)
    @Column(name = "obtainedOutput")
    private String obtainedOutput;
    
    @JoinColumn(name = "OutportVariable_idOutportVariable" ,  referencedColumnName = "idOutportVariable") // updatable = false, insertable = false) //,
    @ManyToOne //(optional = false)
    @JsonBackReference
    private Outportvariable outportVariableidOutportVariable;

    public Result() {
    }

    public Result(Integer idResult) {
        this.idResult = idResult;
    }

    public Integer getIdResult() {
        return idResult;
    }

    public void setIdResult(Integer idResult) {
        this.idResult = idResult;
    }

    public String getNumeroResult() {
        return numeroResult;
    }

    public void setNumeroResult(String numeroResult) {
        this.numeroResult = numeroResult;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getObtainedOutput() {
        return obtainedOutput;
    }

    public void setObtainedOutput(String obtainedOutput) {
        this.obtainedOutput = obtainedOutput;
    }

    public Outportvariable getOutportVariableidOutportVariable() {
        return outportVariableidOutportVariable;
    }

    public void setOutportVariableidOutportVariable(Outportvariable outportVariableidOutportVariable) {
        this.outportVariableidOutportVariable = outportVariableidOutportVariable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResult != null ? idResult.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Result)) {
            return false;
        }
        Result other = (Result) object;
        if ((this.idResult == null && other.idResult != null) || (this.idResult != null && !this.idResult.equals(other.idResult))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.telnet.modelbasedtesting.entities.Result[ idResult=" + idResult + " ]";
    }
    
}
