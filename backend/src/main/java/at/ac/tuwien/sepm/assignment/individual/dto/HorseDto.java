package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.entity.Sex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

public record HorseDto(Long id,
                       @NotNull(message = "Name must not be null")
                       @NotEmpty(message = "Name must not be empty")
                       @NotBlank(message = "Name must not be blank")
                       String name,
                       String description,
                       @NotNull(message = "Birthdate must not be null")
                       @NotEmpty(message = "Birthdate must not be empty")
                       @NotBlank(message = "Birthdate must not be blank")
                       LocalDate birthdate,
                       @NotNull(message = "Sex must not be null")
                       @NotEmpty(message = "Sex must not be empty")
                       @NotBlank(message = "Sex must not be blank")
                       Sex sex,
                       Owner owner,
                       Horse horseFather,
                       Horse horseMother) {
}
