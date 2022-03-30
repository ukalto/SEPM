package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.exception.EndpointException;
import at.ac.tuwien.sepm.assignment.individual.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@RestController
@RequestMapping(OwnerEndpoint.BASE_URL)
public class OwnerEndpoint {
    static final String BASE_URL = "/owners";
    private final OwnerService service;
    private final OwnerMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerEndpoint.class);

    public OwnerEndpoint(OwnerService service, OwnerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Stream<OwnerDto> getAllOwner() {
        LOGGER.info("GET {}", BASE_URL);
        try {
            return service.getAllOwner().stream()
                    .map(mapper::entityToDto);
        } catch (EndpointException e) {
            LOGGER.error("Error in getAllOwner: ",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No owners have been found!");
        }

    }

    @GetMapping("/autocomplete")
    @ResponseStatus(HttpStatus.OK)
    public Stream<OwnerDto> getOwnersAutoComplete(@RequestParam String name) {
        LOGGER.info("GET {}/autocomplete?name={}", BASE_URL, name);
        try {
            return service.getOwnersAutoComplete(name).stream()
                    .map(mapper::entityToDto);
        } catch (EndpointException e) {
            LOGGER.error("Error in getOwnersAutoComplete: ",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no owners which could have been autocompleted");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertOwner(@RequestBody OwnerDto o) {
        LOGGER.info("POST {}", BASE_URL);
        try {
            service.insertOwner(o);
        } catch (EndpointException e) {
            LOGGER.error("Error in insertOwner: ",e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Owner was not inserted");
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOwner(@RequestBody OwnerDto o) {
        LOGGER.info("PUT {}", BASE_URL);
        try {
            service.updateOwner(o);
        } catch (EndpointException e) {
            LOGGER.error("Error in updateOwner: ",e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Owner was not updated");
        }
    }

    @DeleteMapping(path = "/{ownerID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable Long ownerID) {
        LOGGER.info("DELETE {}/{}", BASE_URL, ownerID);
        try {
            service.deleteOwner(ownerID);
        } catch (EndpointException e) {
            LOGGER.error("Error in deleteOwner: ",e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Owner with that id does either not exist or could not have been deleted");
        }
    }
}
