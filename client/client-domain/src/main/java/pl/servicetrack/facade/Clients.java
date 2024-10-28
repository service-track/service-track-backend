package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.ClientDatabaseRepository;
import pl.servicetrack.model.Client;
import pl.servicetrack.model.ClientEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Clients {
    private final ClientDatabaseRepository clientDatabaseRepository;

    public Clients(ClientDatabaseRepository clientDatabaseRepository) {
        this.clientDatabaseRepository = clientDatabaseRepository;
    }

    public Either<Error, Client> addClient(Client client) {
        return clientDatabaseRepository.save(new ClientEntity(
                client.id(),
                client.name(),
                client.email(),
                client.phoneNumber()
        )).map(response -> client);
    }

    public Either<Error, List<Client>> fetchClients() {
        return clientDatabaseRepository.findAll()
                .map(response -> response.stream().map(
                        client -> new Client(
                                client.id(),
                                client.name(),
                                client.email(),
                                client.phoneNumber())
                ).collect(Collectors.toList()));
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
