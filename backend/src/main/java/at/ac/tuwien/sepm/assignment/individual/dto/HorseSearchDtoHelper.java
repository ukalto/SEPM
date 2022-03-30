package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Sex;


public record HorseSearchDtoHelper(String name, String description, String birthdate, Sex sex, String ownerName) {
}