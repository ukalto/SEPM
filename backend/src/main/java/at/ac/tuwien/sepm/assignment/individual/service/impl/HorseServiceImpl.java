package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseService.class);

    public HorseServiceImpl(HorseDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Horse> searchHorses(HorseSearchDto horseSearchDto) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Search all horses {}",horseSearchDto);
        return dao.searchHorses(horseSearchDto);
    }

    @Override
    public Optional<Horse> getHorse(Long id) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Get horse with id {}", id);
        return getHorseWithGenerations(id, 1);
    }

    @Override
    public void insertHorse(HorseDto h) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Insert horse {}", h);
        Horse horse = new Horse(h);
        dao.insertHorse(horse);
    }

    @Override
    public void updateHorse(HorseDto h) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Update horse {}", h);
        Horse horse = new Horse(h);
        dao.updateHorse(horse);
    }

    @Override
    public void deleteHorse(Long id) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Delete horse with id {}", id);
        dao.deleteHorseById(id);
    }

    @Override
    public Optional<Horse> getHorseWithGenerations(Long id, int gens) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Get horses with id {} and its generations {}", id, gens);
        Optional<Horse> horseOptional = Optional.ofNullable(dao.getHorseById(id));
        if (gens <= 1 || horseOptional.isEmpty()) {
            return horseOptional;
        }
        Horse horse = horseOptional.get();
        if (horse.getHorseFather().getId() != null) {
            Optional<Horse> horseFather = getHorseWithGenerations(horse.getHorseFather().getId(), gens - 1);
            horse.setHorseFather(horseFather.orElse(horse.getHorseFather()));
        }
        if (horse.getHorseMother().getId() != null) {
            Optional<Horse> horseMother = getHorseWithGenerations(horse.getHorseMother().getId(), gens - 1);
            horse.setHorseMother(horseMother.orElse(horse.getHorseMother()));
        }
        return horseOptional;
    }

    @Override
    public List<Horse> getHorsesAutoComplete(LocalDate birthdate, String horseName, String gender) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Get all horses which their name come closest to this {} and has the same gender {}", horseName, gender);
        return dao.getHorsesAutoComplete(birthdate, horseName, gender);
    }
}
