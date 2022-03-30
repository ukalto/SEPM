package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;

public class Owner {
    private Long ownerID;
    private String prename;
    private String surname;
    private String email;

    public Owner(Long ownerID, String prename, String surname, String email) {
        this.ownerID = ownerID;
        this.prename = prename;
        this.surname = surname;
        this.email = email;
    }

    public Owner(OwnerDto o) {
        this.ownerID = o.ownerID();
        this.prename = o.prename();
        this.surname = o.surname();
        this.email = o.email();
    }

    public Owner(String prename, String surname, String email){
        this.prename = prename;
        this.surname = surname;
        this.email = email;
    }

    public Owner() {
    }

    public Owner(Long id) {
        this.ownerID = id;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
