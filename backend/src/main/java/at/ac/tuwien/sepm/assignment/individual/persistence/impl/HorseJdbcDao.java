package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.entity.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalHorseException;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalOwnerException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Repository
public class HorseJdbcDao implements HorseDao {
    private static final String TABLE_NAME = "horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Horse> searchHorses(HorseSearchDto horseSearchDto) {
        LOGGER.trace("Search all horses {}", horseSearchDto);
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN owner ON owner.ownerID = " + TABLE_NAME + ".ownerId WHERE " +
                    "(LOWER(name) LIKE :name OR :name IS NULL) AND " +
                    "(LOWER(description) LIKE :description OR :description IS NULL) AND " +
                    "(birthdate = :birthdate OR :birthdate IS NULL) AND " +
                    "(sex = :sex OR :sex IS NULL) AND " +
                    "(LOWER(owner.prename) LIKE :ownerName OR LOWER(owner.surname) LIKE :ownerName OR :ownerName IS NULL)";
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("name", Optional.ofNullable(horseSearchDto.name()).map(name -> "%" + name.toLowerCase() + "%").orElse(null));
            parameters.put("description", Optional.ofNullable(horseSearchDto.description()).map(desc -> "%" + desc.toLowerCase() + "%").orElse(null));
            parameters.put("birthdate", Optional.ofNullable(horseSearchDto.birthdate()).orElse(null));
            parameters.put("sex", Optional.ofNullable(horseSearchDto.sex()).map(sex -> sex.toString().toUpperCase()).orElse(null));
            parameters.put("ownerName", Optional.ofNullable(horseSearchDto.ownerName()).map(ownerName -> "%" + ownerName.toLowerCase() + "%").orElse(null));
            return namedParameterJdbcTemplate.query(sql, parameters, this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public void insertHorse(Horse h) {
        LOGGER.trace("Insert horse {}", h);
        try {
            Long fatherID = h.getHorseFather() == null ? null : h.getHorseFather().getId();
            Long motherID = h.getHorseMother() == null ? null : h.getHorseMother().getId();
            Long ownerID = h.getOwner() == null ? null : h.getOwner().getOwnerID();
            jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (name,description,birthdate,sex,ownerID,horseFatherID,horseMotherID) VALUES (?,?,?,?,?,?,?)",
                    h.getName(),
                    h.getDescription(),
                    h.getBirthdate(),
                    h.getSex().toString().toUpperCase(),
                    ownerID,
                    fatherID,
                    motherID
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalHorseException("Could not insert given horse", e);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public Horse getHorseById(Long id) throws HttpClientErrorException.NotFound {
        LOGGER.trace("Get horse with id {}", id);
        try {
            if (id != null) {
                var temp = jdbcTemplate.query("SELECT * from horse where id = ?", this::mapRow, id);
                return temp.size() == 0 ? null : temp.get(0);
            }
            return null;
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public void updateHorse(Horse h) {
        LOGGER.trace("Update horse {}", h);
        try {
            Long fatherID = h.getHorseFather() == null ? null : h.getHorseFather().getId();
            Long motherID = h.getHorseMother() == null ? null : h.getHorseMother().getId();
            Long ownerID = h.getOwner() == null ? null : h.getOwner().getOwnerID();
            String sql = String.format("UPDATE %s SET name=?, description=?, birthdate=?, sex=?, ownerID=?,horseFatherID=?,horseMotherID=? where id = ?", TABLE_NAME);
            jdbcTemplate.update(sql,
                    h.getName(),
                    h.getDescription(),
                    Optional.ofNullable(h.getBirthdate()).map(LocalDate::toString).orElse(null),
                    h.getSex().toString(),
                    ownerID,
                    fatherID,
                    motherID,
                    h.getId()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalHorseException("Could not edit given horse", e);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public void deleteHorseById(Long id) {
        LOGGER.trace("Delete horse with id {}", id);
        try {
            jdbcTemplate.execute("DELETE From " + TABLE_NAME + " where id = " + id);
        } catch (IllegalArgumentException e) {
            throw new IllegalHorseException("There is no horse to delete with this id.", e);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public List<Horse> getHorsesAutoComplete(LocalDate birthdate, String horseName, String gender) {
        LOGGER.trace("Get all horses which their name come closest to this {} and has the same gender {}", horseName, gender);
        try {
            String sql = String.format("SELECT * FROM %s WHERE LOWER(name) LIKE ? AND ? > birthdate AND sex = ? LIMIT 5", TABLE_NAME);
            return jdbcTemplate.query(sql, this::mapHorseRowWithoutOwner, "%" + horseName.toLowerCase() + "%", birthdate, gender.toUpperCase());
        } catch (DataAccessException e) {
            throw new IllegalOwnerException("Could not query all owners", e);
        }
    }

    private Owner getOwnerByID(Long ownerID) {
        try {
            if (ownerID != null) {
                var temp = jdbcTemplate.query("SELECT * from owner where ownerID = " + ownerID, this::mapRowOwner);
                return temp.size() == 0 ? null : temp.get(0);
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw new IllegalHorseException("There is no existing owner with this id.", e);
        }
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setBirthdate(result.getDate("birthdate").toLocalDate());
        horse.setSex(Sex.valueOf(result.getString("sex")));
        horse.setOwner(getOwnerByID(result.getLong("ownerID")));
        horse.setHorseFather(new Horse(result.getLong("horseFatherID")));
        horse.setHorseMother(new Horse(result.getLong("horseMotherID")));
        return horse;
    }

    private Horse mapHorseRowWithoutOwner(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setBirthdate(result.getDate("birthdate").toLocalDate());
        horse.setSex(Sex.valueOf(result.getString("sex")));
        horse.setOwner(new Owner(result.getLong("ownerID")));
        horse.setHorseFather(new Horse(result.getLong("horseFatherID")));
        horse.setHorseMother(new Horse(result.getLong("horseMotherID")));
        return horse;
    }

    private Owner mapRowOwner(ResultSet result, int rownum) throws SQLException {
        Owner owner = new Owner();
        owner.setOwnerID(result.getLong("ownerID"));
        owner.setPrename(result.getString("prename"));
        owner.setSurname(result.getString("surname"));
        owner.setEmail(result.getString("email"));
        return owner;
    }
}
