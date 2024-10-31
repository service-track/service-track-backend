package pl.servicetrack.configuration;

import org.springframework.context.annotation.Bean;
import pl.servicetrack.db.ClientDatabaseRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.facade.Clients;

@Configuration
public class ClientConfiguration {
    @Bean
    ClientDatabaseRepository clientDatabaseRepository(JdbcTemplate jdbcTemplate) {
        return new ClientDatabaseRepository(jdbcTemplate);
    }

    @Bean
    Clients clients(ClientDatabaseRepository clientDatabaseRepository) {
        return new Clients(clientDatabaseRepository);
    }
}
