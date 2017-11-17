/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.igac.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jvega
 */
@Entity
@Table(name = "GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g")
    , @NamedQuery(name = "Groups.findById", query = "SELECT g FROM Groups g WHERE g.id = :id")
    , @NamedQuery(name = "Groups.findByGroupname", query = "SELECT id FROM Groups g WHERE g.groupname in( :groupname,'rest-all','kie-server')")
    , @NamedQuery(name = "Groups.findByObservaciones", query = "SELECT g FROM Groups g WHERE g.observaciones = :observaciones")})
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G2")
    @SequenceGenerator(name = "G2", sequenceName = "SEQUENCE_GROUPS")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "GROUPNAME")
    private String groupname;
    @Size(max = 255)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USERGROUPS", joinColumns = {
        @JoinColumn(name = "IDGROUPNAME", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "IDUSERNAME", referencedColumnName = "ID")})
    
    private Set<Users> usersCollection = new  HashSet<>();

    public Groups() {
    }

    public Groups(BigDecimal id) {
        this.id = id;
    }

    public Groups(BigDecimal id, String groupname) {
        this.id = id;
        this.groupname = groupname;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    @JsonIgnore
    public Set<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Set<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.igac.jbpm.entity.Groups[ id=" + id + " ]";
    }
    
}