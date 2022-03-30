package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDate;
import java.util.List;

public interface HorseDao {
    /**
     * Get all horses with given search properties (name,description,birthdate,sex,ownerName)
     * @return returns list of found horses
     * @throws if you have no connection to the database it will throw a PersistenceException
     */
    List<Horse> searchHorses(HorseSearchDto horseSearchDto);

    /**
     * Get horse by id
     * @return returns horse with id
     * @throws if there is a problem throw a HttpClientErrorException.NotFound
     */
    Horse getHorseById(Long id);

    /**
     * Inserts horse
     * @params the name, the birthdate and sex of h has to be set the rest can be null
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     * or if you have no connection to the database it will throw a PersistenceException
     */
    void insertHorse(Horse h);

    /**
     * Updates horse
     * @params the name, the birthdate and sex of h has to be set the rest can be null
     * @throws if you pass the wrong arguments it will catch a IllegalArgumentException e and throws a PersistenceException
     *  or if you have no connection to the database it will throw a PersistenceException
     */
    void updateHorse(Horse h);

    /**
     * Deletes horse via id
     * @params id has to be valid
     * @throws if you pass the wrong arguments it will catch a IllegalArgumentException e and throws a PersistenceException
     *  or if you have no connection to the database it will throw a PersistenceException
     */
    void deleteHorseById(Long id);

    /**
     * Get 5 closest matches where birthdate is after all others, the horseName which represents the input is Like the
     * horse names in the database and the gender in the database matches the given gender
     * @param birthdate, horseName, gender has to be valid
     * @return if you have no connection to the database it will throw a PersistenceException
     */
    List<Horse> getHorsesAutoComplete(LocalDate birthdate, String horseName, String gender);
}
