package pl.servicetrack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.db.ServiceOrderDatabaseRepository;
import pl.servicetrack.facade.ServiceOrders;

@Configuration
public class ServiceOrderConfiguration {
    @Bean
    ServiceOrderDatabaseRepository serviceOrderDatabaseRepository(JdbcTemplate jdbcTemplate) {
        return new ServiceOrderDatabaseRepository(jdbcTemplate);
    }
    @Bean
    ServiceOrders serviceOrders(ServiceOrderDatabaseRepository serviceOrderDatabaseRepository) {
        return new ServiceOrders(serviceOrderDatabaseRepository);
    }
}
