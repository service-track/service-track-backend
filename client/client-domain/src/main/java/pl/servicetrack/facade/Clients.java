package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.ClientDatabaseRepository;
import pl.servicetrack.model.Client;
import pl.servicetrack.model.ClientModel;

import java.util.UUID;

public class Clients {
    private final ClientDatabaseRepository clientDatabaseRepository;

    public Clients(ClientDatabaseRepository clientDatabaseRepository) {
        this.clientDatabaseRepository = clientDatabaseRepository;
    }

    public Either<Error, Client> addClient(Client client) {
        return clientDatabaseRepository.save(new ClientModel(
                client.id(),
                client.name(),
                client.email(),
                client.phoneNumber()
        )).map(response -> client);
    }

    public Either<Error, Client> fetchClient(UUID clientId) {
        return clientDatabaseRepository.find(clientId)
                .map(response -> new Client(
                        response.id(),
                        response.name(),
                        response.email(),
                        response.phoneNumber()
                ));
    }

    public Either<Error, Integer> deleteClient(UUID clientId) {
        return clientDatabaseRepository.delete(clientId);
    }
}
