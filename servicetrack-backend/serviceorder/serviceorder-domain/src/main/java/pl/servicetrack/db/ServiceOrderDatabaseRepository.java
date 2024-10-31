package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.entity.ServiceOrderEntity;

import java.util.List;
import java.util.UUID;

import static org.springframework.dao.support.DataAccessUtils.singleResult;
import static pl.servicetrack.db.ServiceOrderQuery.*;

public class ServiceOrderDatabaseRepository {
    private final static String FAILED_TO_SAVE_SERVICEORDER = "Failed to save service order!";
    private final static String SUCCESSFULLY_SAVED_SERVICEORDER = "Successfully saved service order!";
    private final static String FAILED_TO_FETCH_SERVICEORDERS = "Failed to fetch service orders!";
    private final static String SUCCESSFULLY_FETCHED_SERVICEORDERS = "Successfully fetched service orders!";
    private final static String FAILED_TO_FETCH_SERVICEORDER = "Failed to fetch service order!";
    private final static String SUCCESSFULLY_FETCHED_SERVICEORDER = "Successfully fetched service order!";
    private final static String FAILED_TO_DELETE_SERVICEORDER = "Failed to delete service order!";
    private final static String SUCCESSFULLY_DELETED_SERVICEORDER = "Successfully deleted service order!";
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

    public Either<Error, List<ServiceOrderEntity>> findAll() {
        return Try.of(() -> jdbcTemplate.query(FETCH_SERVICEORDERS, SERVICEORDER_MAPPER))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_SERVICEORDERS))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_SERVICEORDERS))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_SERVICEORDERS));
    }

    public Either<Error, ServiceOrderEntity> find(UUID serviceOrderId) {
        return Try.of(() -> singleResult(jdbcTemplate.query(FETCH_SERVICEORDER, SERVICEORDER_MAPPER, serviceOrderId)))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_SERVICEORDER))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_SERVICEORDER))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_SERVICEORDER));
    }

    public Either<Error, Integer> delete(UUID serviceOrderId) {
        return Try.of(() -> jdbcTemplate.update(DELETE_SERVICEORDER, serviceOrderId))
                .onFailure(error -> LOGGER.warn(FAILED_TO_DELETE_SERVICEORDER))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_DELETED_SERVICEORDER))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_DELETE_SERVICEORDER, error));
    }
}
