package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;

import java.time.LocalDate;

public class Horse {
    private Long id;
    private String name;
    private String description;
    private LocalDate birthdate;
    private Sex sex;
    private Owner owner;
    private Horse horseFather;
    private Horse horseMother;

    public Horse(Long id, String name, String description, LocalDate birthdate, Sex sex, Owner owner, Horse horseFather, Horse horseMother) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthdate = birthdate;
        this.sex = sex;
        this.owner = owner;
        this.horseFather = horseFather;
        this.horseMother = horseMother;
    }

    public Horse() {

    }

    public Horse(Long id) {
        this.id = id;
    }

    public Horse(HorseDto h) {
        this.id = h.id();
        this.name = h.name();
        this.description = h.description();
        this.birthdate = h.birthdate();
        this.sex = h.sex();
        this.owner = h.owner();
        this.horseFather = h.horseFather();
        this.horseMother = h.horseMother();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Horse getHorseFather() {
        return horseFather;
    }

    public void setHorseFather(Horse horseFather) {
        this.horseFather = horseFather;
    }

    public Horse getHorseMother() {
        return horseMother;
    }

    public void setHorseMother(Horse horseMother) {
        this.horseMother = horseMother;
    }
}
