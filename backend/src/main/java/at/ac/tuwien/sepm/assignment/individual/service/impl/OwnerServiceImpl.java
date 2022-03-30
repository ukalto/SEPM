package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerServiceImpl.class);

    public OwnerServiceImpl(OwnerDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Owner> getAllOwner() throws HttpClientErrorException.NotFound {
        LOGGER.trace("Get all owners");
        return dao.getAllOwner();
    }

    @Override
    public void insertOwner(OwnerDto o) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Insert owner {}", o);
        Owner owner = new Owner(o);
        dao.insertOwner(owner);
    }

    @Override
    public void updateOwner(OwnerDto o) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Update owner {}", o);
        Owner owner = new Owner(o);
        dao.updateOwner(owner);
    }

    @Override
    public void deleteOwner(Long ownerID) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Delete owner with id {}", ownerID);
        dao.deleteOwner(ownerID);
    }

    @Override
    public List<Owner> getOwnersAutoComplete(String name) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Get all owners which their name come closest to this {}", name);
        return dao.getOwnersAutocomplete(name);
    }
}
