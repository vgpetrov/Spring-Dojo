package ru.db.entities;
// Generated Jan 5, 2013 11:31:32 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Deviation generated by hbm2java
 */
@Entity
@Table(name="DEVIATION", schema="APP")
public class Deviation implements java.io.Serializable {

    @Id 
    @Column(name="ID", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="NUMBER")
    private Integer number;
    
    @Column(name="VALUE", precision=52, scale=0)
    private Double value;
    
    @Column(name="IS_ACTIVE")
    private Short isActive;

    public Deviation() {
    }
	
    public Deviation(Long id) {
        this.id = id;
    }
    public Deviation(Long id, Integer number, Double value, Short isActive) {
       this.id = id;
       this.number = number;
       this.value = value;
       this.isActive = isActive;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
        public Integer getNumber() {
        return this.number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public Double getValue() {
        return this.value;
    }
    
    public void setValue(Double value) {
        this.value = value;
    }
    
        public Short getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Deviation{" + "id=" + id + ", number=" + number + ", value=" + value + ", isActive=" + isActive + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 47 * hash + (this.number != null ? this.number.hashCode() : 0);
        hash = 47 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 47 * hash + (this.isActive != null ? this.isActive.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Deviation other = (Deviation) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.number != other.number && (this.number == null || !this.number.equals(other.number))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

}

