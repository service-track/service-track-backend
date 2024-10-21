package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.controller.model.ClientModel;

import static pl.servicetrack.db.ClientQuery.*;

public class ClientDatabaseRepository {
    private final String FAILED_TO_SAVE_CLIENT = "Failed to save client!";
    private final String SUCCESSFULLY_SAVED_CLIENT = "Successfully saved client!";
    public final JdbcTemplate jdbcTemplate;
    public final Logger LOGGER = LoggerFactory.getLogger(ClientDatabaseRepository.class);

    public ClientDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Either<Error, ClientModel> save(ClientModel clientModel) {
        return Try.of(() -> jdbcTemplate.update(SAVE_CLIENT,
                        clientModel.id(),
                        clientModel.name(),
                        clientModel.email(),
                        clientModel.phoneNumber()))
                .onFailure(error -> LOGGER.warn(FAILED_TO_SAVE_CLIENT))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_SAVED_CLIENT))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_SAVE_CLIENT, error))
                .map(value -> clientModel);
    }
}
