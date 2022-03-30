package at.ac.tuwien.sepm.assignment.individual.persistence.rowmapper;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OwnerRowMapper implements RowMapper<Owner> {
    @Override
    public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
        Owner owner = new Owner();
        owner.setOwnerID(rs.getLong("ownerID"));
        owner.setPrename(rs.getString("prename"));
        owner.setSurname(rs.getString("surname"));
        owner.setEmail(rs.getString("email"));
        return owner;
    }
}
