package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.model.Client;
import pl.servicetrack.model.ClientModel;

import java.util.UUID;

import static pl.servicetrack.db.ClientQuery.*;

public class ClientDatabaseRepository {
    private final String FAILED_TO_SAVE_CLIENT = "Failed to save client!";
    private final String SUCCESSFULLY_SAVED_CLIENT = "Successfully saved client!";
    private final String FAILED_TO_FETCH_CLIENT = "Failed to fetch client!";
    private final String SUCCESSFULLY_FETCHED_CLIENT = "Successfully fetched client!";
    private final ClientMapper CLIENT_MAPPER = new ClientMapper();
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

    public Either<Error, ClientModel> find(UUID clientId) {
        return Try.of(() -> jdbcTemplate.queryForObject(FETCH_CLIENT, CLIENT_MAPPER, clientId))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_CLIENT))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_CLIENT))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_CLIENT, error));
    }
}
