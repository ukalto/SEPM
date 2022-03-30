package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;

import java.util.List;

public interface OwnerService {
    /**
     * Get all owners
     * @return returns list of found owners
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    List<Owner> getAllOwner();

    /**
     * Inserts owners
     * @params the prename and the surname of o has to be set email can be null
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    void insertOwner(OwnerDto o) ;

    /**
     * Updates owner
     * @params the prename and the surname of o has to be set email can be null
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    void updateOwner(OwnerDto o) ;

    /**
     * Deletes owner via id
     * @params ownerID has to be valid
     * @throws if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    void deleteOwner(Long ownerID) ;

    /**
     * Get 5 closest named existing owners
     * @param name has to be valid
     * @return if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
     */
    List<Owner> getOwnersAutoComplete(String name);
}
