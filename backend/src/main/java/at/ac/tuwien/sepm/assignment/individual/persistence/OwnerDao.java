package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;

import java.util.List;

public interface OwnerDao {
    /**
     * Get all owners
     * @return returns list of found owners
     * @throws if you have no connection to the database it will throw a PersistenceException
     */
    List<Owner> getAllOwner();

    /**
     * Inserts owners
     * @params the prename and the surname of o has to be set email can be null
     * @throws if you pass the wrong arguments it will catch a IllegalArgumentException e and throws a PersistenceException
     * or if you have no connection to the database it will throw a PersistenceException
     */
    void insertOwner(Owner o);

    /**
     * Updates owner
     * @params the prename and the surname of o has to be set email can be null
     * @throws if you pass the wrong arguments it will catch a IllegalArgumentException e and throws a PersistenceException
     *  or if you have no connection to the database it will throw a PersistenceException
     */
    void updateOwner(Owner o);

    /**
     * Deletes owner via id
     * @params ownerID has to be valid
     * @throws if you pass the wrong arguments it will catch a IllegalArgumentException e and throws a PersistenceException
     *  or if you have no connection to the database it will throw a PersistenceException
     */
    void deleteOwner(Long ownerID);

    /**
     * Get 5 closest named existing owners
     * @param name has to be valid
     * @return if you have no connection to the database it will throw a PersistenceException
     */
    List<Owner> getOwnersAutocomplete(String name);
}
