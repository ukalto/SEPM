package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.rowmapper.OwnerRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OwnerJbdcDao implements OwnerDao {
    private static final String TABLE_NAME = "owner";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerJbdcDao.class);

    private final JdbcTemplate jdbcTemplate;

    public OwnerJbdcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Owner> getAllOwner() {
        LOGGER.info("Get all owners");
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, new OwnerRowMapper());
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query owners", e);
        }
    }

    @Override
    public void insertOwner(Owner o) {
        LOGGER.info("Insert owner {}", o);
        try {
            jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (prename,surname,email) VALUES (?, ?, ?)", o.getPrename(), o.getSurname(), o.getEmail());
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Could not insert given owner", e);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public void updateOwner(Owner o) {
        LOGGER.info("Update owner {}", o);
        try {
            String sql = String.format("UPDATE %s SET prename = ?, surname = ?, email = ? where ownerID = ?", TABLE_NAME);
            jdbcTemplate.update(sql, o.getPrename(), o.getSurname(), o.getEmail(), o.getOwnerID());
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Could not edit given owner", e);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public void deleteOwner(Long ownerID) {
        LOGGER.info("Delete owner with id {}", ownerID);
        try {
            String sql = String.format("DELETE From %s where ownerID = %s", TABLE_NAME, ownerID);
            jdbcTemplate.execute(sql);
        } catch (NoSuchFieldError e) {
            throw new PersistenceException("There is no owner with this ownerID", e);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not access database", e);
        }
    }

    @Override
    public List<Owner> getOwnersAutocomplete(String name) {
        LOGGER.info("Get all owners which their name come closest to this {}", name);
        try {
            String sql = String.format("SELECT * FROM %s WHERE LOWER(prename) LIKE ? or LOWER(surname) LIKE ? LIMIT 5", TABLE_NAME);
            return jdbcTemplate.query(sql, new OwnerRowMapper(), "%" + name.toLowerCase() + "%", "%" + name.toLowerCase() + "%");
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query owners", e);
        }
    }
}
