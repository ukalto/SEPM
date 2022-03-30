package at.ac.tuwien.sepm.assignment.individual.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

@Component
@Profile("datagen")
public class DataGeneratorBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DataSource dataSource;

    public DataGeneratorBean(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void generateData() throws SQLException {
        LOGGER.info("Generating data…");
        try (var connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/insertData.sql"));
            LOGGER.info("Finished generating data without error.");
        }
    }
}
