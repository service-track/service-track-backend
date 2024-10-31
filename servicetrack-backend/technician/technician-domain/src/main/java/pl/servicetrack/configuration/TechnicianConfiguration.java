package pl.servicetrack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.db.TechnicianDatabaseRepository;
import pl.servicetrack.facade.Technicians;

@Configuration
public class TechnicianConfiguration {
    @Bean
    TechnicianDatabaseRepository technicianDatabaseRepository(JdbcTemplate jdbcTemplate){
        return new TechnicianDatabaseRepository(jdbcTemplate);
    }
    @Bean
    Technicians technicians(TechnicianDatabaseRepository technicianDatabaseRepository){
        return new Technicians(technicianDatabaseRepository);
    }
}
