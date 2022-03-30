package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HorseService {
    /**
     * Get all horses with given search properties (name,description,birthdate,sex,ownerName)
     * @return returns list of found horses
     * @throws if there are no horses it will throw a HttpClientErrorException.NotFound
     */
    List<Horse> searchHorses(HorseSearchDto horseSearchDto);

    /**
     * Get horse by id
     * @return returns horse with id
     * @throws if there is a problem throw a HttpClientErrorException.NotFound
     */
    Optional<Horse> getHorse(Long id);

    /**
     * Inserts horse
     * @params the name, the birthdate and sex of h has to be set the rest can be null
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    void insertHorse(HorseDto h);

    /**
     * Updates horse
     * @params the name, the birthdate and sex of h has to be set the rest can be null
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    void updateHorse(HorseDto h);

    /**
     * Deletes horse via id
     * @params id has to be valid
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    void deleteHorse(Long id);

    /**
     *  Returns a horse with all corresponding horses
     * @param id must exist, gens has to be > 0
     * @return returns an optional horse and goes throw one horse recursively to get all corresponding horses
     */
    Optional<Horse> getHorseWithGenerations(Long id, int gens);

    /**
     * Get 5 closest matches where birthdate is after all others, the horseName which represents the input is Like the
     * horse names in the database and the gender in the database matches the given gender
     * @param birthdate, horseName, gender has to be valid
     * @return if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    List<Horse> getHorsesAutoComplete(LocalDate birthdate, String horseName, String gender);
}
