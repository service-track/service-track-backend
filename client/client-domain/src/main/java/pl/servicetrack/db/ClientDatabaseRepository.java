package pl.servicetrack.db;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.servicetrack.model.ClientEntity;

import java.util.List;
import java.util.UUID;

import static pl.servicetrack.db.ClientQuery.*;

public class ClientDatabaseRepository {
    private final String FAILED_TO_SAVE_CLIENT = "Failed to save client!";
    private final String SUCCESSFULLY_SAVED_CLIENT = "Successfully saved client!";
    private final String FAILED_TO_FETCH_CLIENT = "Failed to fetch client!";
    private final String SUCCESSFULLY_FETCHED_CLIENT = "Successfully fetched client!";
    private final String FAILED_TO_FETCH_CLIENTS = "Failed to fetch clients!";
    private final String SUCCESSFULLY_FETCHED_CLIENTS = "Successfully fetched clients!";
    private final String FAILED_TO_DELETE_CLIENT = "Failed to delete client!";
    private final String SUCCESSFULLY_DELETED_CLIENT = "Successfully deleted client!";
    private final ClientMapper CLIENT_MAPPER = new ClientMapper();
    public final JdbcTemplate jdbcTemplate;
    public final Logger LOGGER = LoggerFactory.getLogger(ClientDatabaseRepository.class);

    public ClientDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Either<Error, ClientEntity> save(ClientEntity clientEntity) {
        return Try.of(() -> jdbcTemplate.update(SAVE_CLIENT,
                        clientEntity.id(),
                        clientEntity.name(),
                        clientEntity.email(),
                        clientEntity.phoneNumber()))
                .onFailure(error -> LOGGER.warn(FAILED_TO_SAVE_CLIENT))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_SAVED_CLIENT))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_SAVE_CLIENT, error))
                .map(value -> clientEntity);
    }

    public Either<Error, List<ClientEntity>> findAll() {
        return Try.of(() -> jdbcTemplate.query(FETCH_CLIENTS, CLIENT_MAPPER))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_CLIENTS))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_CLIENTS))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_CLIENTS, error));
    }

    public Either<Error, ClientEntity> find(UUID clientId) {
        return Try.of(() -> jdbcTemplate.queryForObject(FETCH_CLIENT, CLIENT_MAPPER, clientId))
                .onFailure(error -> LOGGER.warn(FAILED_TO_FETCH_CLIENT))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_FETCHED_CLIENT))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_FETCH_CLIENT, error));
    }

    public Either<Error, Integer> delete(UUID clientId) {
        return Try.of(() -> jdbcTemplate.update(DELETE_CLIENT, clientId))
                .onFailure(error -> LOGGER.warn(FAILED_TO_DELETE_CLIENT))
                .onSuccess(success -> LOGGER.info(SUCCESSFULLY_DELETED_CLIENT))
                .toEither()
                .mapLeft(error -> new Error(FAILED_TO_DELETE_CLIENT, error));
    }
}
