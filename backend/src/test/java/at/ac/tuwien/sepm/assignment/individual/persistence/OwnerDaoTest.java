package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class OwnerDaoTest {

    @Autowired
    private OwnerDao ownerDao;

    @Test
    public void testGetOwnersAutocomplete_shouldReturnOneOwner() {
        List<Owner> results = ownerDao.getOwnersAutocomplete("lev");

        Assertions.assertEquals(1, results.size());
    }

    @Test
    public void testGetOwnersAutocomplete_shouldReturnMax5() {
        List<Owner> results = ownerDao.getOwnersAutocomplete("a");

        Assertions.assertEquals(5, results.size());
    }

    @Test
    public void insertOwner(){
//        Owner owner = new Owner(100L,"aaa","a","");
//        ownerDao.insertOwner(owner);
//        List<Owner> results = ownerDao.getOwnersAutocomplete("a");
//        Assertions.assertEquals(6, results.size());
    }
}
