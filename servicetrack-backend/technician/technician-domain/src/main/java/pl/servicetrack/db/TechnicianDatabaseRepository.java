package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.entity.TechnicianEntity;

import java.util.List;
import java.util.UUID;

import static pl.servicetrack.db.TechnicianQuery.*;

public class TechnicianDatabaseRepository {
    private final static String FAILED_TO_SAVE_TECHNICIAN = "Failed to save technician!";
    private final static String SUCCESSFULLY_SAVED_EMERGENCY = "Successfully save technician!";
    private final static String FAILED_TO_FETCH_TECHNICIANS = "Failed to fetch technicians!";
    private final static String SUCCESSFULLY_FETCHED_TECHNICIANS = "Successfully fetched technicians!";
    private final static String FAILED_TO_FETCH_TECHNICIAN = "Failed to fetch technician!";
    private final static String SUCCESSFULLY_FETCHED_TECHNICIAN = "Successfully fetched technician!";
    private final static String FAILED_TO_DELETE_TECHNICIAN = "Failed to delete technician!";
    private final static String SUCCESSFULLY_DELETED_TECHNICIAN = "Successfully deleted technician!";
    private final TechnicianMapper TECHNICIAN_MAPPER = new TechnicianMapper();
    private final JdbcTemplate jdbcTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(TechnicianDatabaseRepository.class);

    public TechnicianDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Either<Error, TechnicianEntity> save(TechnicianEntity technicianEntity) {
        return Try.of(() -> jdbcTemplate.update(SAVE_TECHNICIAN,
                        technicianEntity.id(),
                        technicianEntity.firstName(),
                        technicianEntity.lastName(),
                        technicianEntity.email(),
                        technicianEntity.phoneNumber()))
                .onFailure(error -> LOGGER.warn(FAILED_TO_SAVE_TECHNICIAN))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_SAVED_EMERGENCY))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_SAVE_TECHNICIAN, error))
                .map(value -> technicianEntity);
    }
    public Either<Error, List<TechnicianEntity>> findAll() {
        return Try.of(() -> jdbcTemplate.query(FETCH_TECHNICIANS, TECHNICIAN_MAPPER))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_TECHNICIANS))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_TECHNICIANS))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_TECHNICIANS, error));
    }
    public Either<Error, TechnicianEntity> find(UUID technicianId) {
        return Try.of(() -> jdbcTemplate.queryForObject(FETCH_TECHNICIAN, TECHNICIAN_MAPPER, technicianId))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_TECHNICIAN))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_TECHNICIAN))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_TECHNICIAN, error));
    }

    public Either<Error, Integer> delete(UUID technicianId) {
        return Try.of(() -> jdbcTemplate.update(DELETE_TECHNICIAN, technicianId))
                .onFailure(error -> LOGGER.warn(FAILED_TO_DELETE_TECHNICIAN))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_DELETED_TECHNICIAN))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_TECHNICIAN, error));
    }
}
