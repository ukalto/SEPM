package at.ac.tuwien.sepm.assignment.individual.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record OwnerDto(Long ownerID,
                       @NotNull(message = "Prename must not be null")
                       @NotEmpty(message = "Prename must not be empty")
                       @NotBlank(message = "Prename must not be blank")
                       String prename,
                       @NotNull(message = "Surname must not be null")
                       @NotEmpty(message = "Surname must not be empty")
                       @NotBlank(message = "Surname must not be blank")
                       String surname,
                       @Email
                       String email) {
}
