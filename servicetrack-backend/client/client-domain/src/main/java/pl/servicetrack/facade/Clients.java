package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.ClientDatabaseRepository;
import pl.servicetrack.model.Client;

import java.util.List;
import java.util.UUID;

public class Clients {
    private final ClientDatabaseRepository clientDatabaseRepository;
    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    public Clients(ClientDatabaseRepository clientDatabaseRepository) {
        this.clientDatabaseRepository = clientDatabaseRepository;
    }

    public Either<Error, Client> addClient(Client client) {
        return clientDatabaseRepository.save(
                        clientMapper.clientToClientEntity(client))
                .map(response -> client);
    }

    public Either<Error, List<Client>> fetchClients() {
        return clientDatabaseRepository.findAll()
                .map(clientMapper::clientEntitiesToClients);
    }

    public Either<Error, Client> fetchClient(UUID clientId) {
        return clientDatabaseRepository.find(clientId)
                .map(clientMapper::clientEntityToClient);
    }

    public Either<Error, Integer> deleteClient(UUID clientId) {
        return clientDatabaseRepository.delete(clientId);
    }
}
