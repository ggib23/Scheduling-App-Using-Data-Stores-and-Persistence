package com.udacity.jdnd.course3.critter.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Nationalized;

import com.udacity.jdnd.course3.critter.pet.Pet;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;

    @Nationalized
    private String phoneNumber;

    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "customer", targetEntity = Pet.class, cascade = CascadeType.ALL)
    private List<Pet> pets;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    
}
