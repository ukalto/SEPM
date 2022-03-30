package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDtoHelper;
import at.ac.tuwien.sepm.assignment.individual.exception.EndpointException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.stream.Stream;

@RestController
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {
    static final String BASE_URL = "/horses";
    private final HorseService service;
    private final HorseMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseEndpoint.class);


    public HorseEndpoint(HorseService service, HorseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Stream<HorseDto> searchHorses(HorseSearchDtoHelper hsDtoH) {
        LOGGER.info("GET {} {}", BASE_URL, hsDtoH);
        try {
            if (hsDtoH.birthdate() != null) {
                String[] birth = hsDtoH.birthdate().replace("'","").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]), Integer.parseInt(birth[2]));
                HorseSearchDto hsDto = new HorseSearchDto(hsDtoH.name(), hsDtoH.description(), date, hsDtoH.sex(), hsDtoH.ownerName());
                return service.searchHorses(hsDto).stream()
                        .map(mapper::entityToDto);
            }
            else{
                HorseSearchDto hsDto = new HorseSearchDto(hsDtoH.name(), hsDtoH.description(), null, hsDtoH.sex(), hsDtoH.ownerName());
                return service.searchHorses(hsDto).stream()
                        .map(mapper::entityToDto);
            }
        } catch (EndpointException e) {
            LOGGER.error("Error in searchHorses: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No horses with the given filter have been found!");
        }

    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto getHorse(@PathVariable Long id) {
        LOGGER.info("GET {}/{}", BASE_URL, id);
        try {
            return service.getHorse(id).map(mapper::entityToDto).orElse(null);
        } catch (EndpointException e) {
            LOGGER.error("Error in getHorse: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No horse with id has been found");
        }
    }

    @GetMapping(path = "/{id}/familytree")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto getHorseWithGenerations(@PathVariable Long id, @RequestParam int generations) {
        LOGGER.info("GET {}/{}", BASE_URL, id);
        try {
            return service.getHorseWithGenerations(id, generations).map(mapper::entityToDto).orElse(null);
        } catch (EndpointException e) {
            LOGGER.error("Error in getHorseWithGenerations: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Horses have been found");
        }
    }

    @GetMapping("/autocomplete")
    @ResponseStatus(HttpStatus.OK)
    public Stream<HorseDto> getHorsesAutoComplete(@RequestParam String birthdate, @RequestParam String horseName, @RequestParam String gender) {
        LOGGER.info("GET {}/autocomplete?birthdate={}horseName={}gender={}", BASE_URL, birthdate, horseName, gender);
        try {
            String[] birth = birthdate.split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]), Integer.parseInt(birth[2]));
            return service.getHorsesAutoComplete(date, horseName, gender).stream()
                    .map(mapper::entityToDto);
        } catch (EndpointException e) {
            LOGGER.error("Error in getHorsesAutoComplete: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No possible autocompletes");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertHorse(@RequestBody HorseDto h) {
        LOGGER.info("POST {}", BASE_URL);
        try {
            service.insertHorse(h);
        } catch (EndpointException e) {
            LOGGER.error("Error in insertHorse: ", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't insert horse");
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateHorse(@RequestBody HorseDto h) {
        LOGGER.info("PUT {}", BASE_URL);
        try {
            service.updateHorse(h);
        } catch (EndpointException e) {
            LOGGER.error("Error in updateHorse: ", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't update horse");
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHorse(@PathVariable Long id) {
        LOGGER.info("DELETE {}/{}", BASE_URL, id);
        try {
            service.deleteHorse(id);
        } catch (EndpointException e) {
            LOGGER.error("Error in deleteHorse: ", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't delete horse");
        }
    }
}
