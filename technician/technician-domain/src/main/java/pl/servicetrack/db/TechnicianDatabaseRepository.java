package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.model.TechnicianModel;

import static pl.servicetrack.db.TechnicianQuery.*;

public class TechnicianDatabaseRepository {
    private final static String FAILED_TO_SAVE_TECHNICIAN = "Failed to save technician!";
    private final static String SUCCESSFULLY_SAVED_EMERGENCY = "Successfully save technician!";
    private final TechnicianMapper TECHNICIAN_MAPPER = new TechnicianMapper();
    private final JdbcTemplate jdbcTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(TechnicianDatabaseRepository.class);

    public TechnicianDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Either<Error, TechnicianModel> save(TechnicianModel technicianModel) {
        return Try.of(() -> jdbcTemplate.update(SAVE_TECHNICIAN,
                        technicianModel.id(),
                        technicianModel.firstName(),
                        technicianModel.lastName(),
                        technicianModel.email(),
                        technicianModel.phoneNumber()))
                .onFailure(error -> LOGGER.warn(FAILED_TO_SAVE_TECHNICIAN))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_SAVED_EMERGENCY))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_SAVE_TECHNICIAN, error))
                .map(value -> technicianModel);
    }
}
