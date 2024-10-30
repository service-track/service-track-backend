package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.entity.ServiceOrderEntity;

import static pl.servicetrack.db.ServiceOrderQuery.*;

public class ServiceOrderDatabaseRepository {
    private final static String FAILED_TO_SAVE_SERVICEORDER = "Failed to save service order!";
    private final static String SUCCESSFULLY_SAVED_SERVICEORDER = "Successfully saved service order!";
    private final JdbcTemplate jdbcTemplate;
    private final ServiceOrderMapper SERVICEORDER_MAPPER = new ServiceOrderMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(ServiceOrderDatabaseRepository.class);
    public ServiceOrderDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Either<Error, ServiceOrderEntity> save(ServiceOrderEntity serviceOrderEntity) {
        return Try.of(() -> jdbcTemplate.update(SAVE_SERVICEORDER,
                serviceOrderEntity.id(),
                serviceOrderEntity.technicianId(),
                serviceOrderEntity.clientId(),
                serviceOrderEntity.serviceType().name(),
                serviceOrderEntity.serviceFormat().name(),
                serviceOrderEntity.serviceDescription(),
                serviceOrderEntity.dateTimeOfService(),
                serviceOrderEntity.status().name(),
                serviceOrderEntity.serviceDuration(),
                serviceOrderEntity.comment(),
                serviceOrderEntity.creationDateTime()))
                .onFailure(error -> LOGGER.warn(FAILED_TO_SAVE_SERVICEORDER))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_SAVED_SERVICEORDER))
                .toEither()
                .map(value -> serviceOrderEntity)
                .mapLeft(error -> new Error(FAILED_TO_SAVE_SERVICEORDER, error));
    }
}
