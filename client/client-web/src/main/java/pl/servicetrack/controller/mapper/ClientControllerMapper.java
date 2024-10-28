package pl.servicetrack.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.controller.model.AddClientRequest;
import pl.servicetrack.controller.model.AddClientResponse;
import pl.servicetrack.controller.model.FetchClientResponse;
import pl.servicetrack.controller.model.FetchClientsResponse;
import pl.servicetrack.model.Client;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ClientControllerMapper {
    ClientControllerMapper INSTANCE = Mappers.getMapper(ClientControllerMapper.class);

    FetchClientResponse clientToFetchClientResponse(Client client);
    FetchClientsResponse.Client mapToFetchClientsResponse(Client client);
    default FetchClientsResponse clientsToFetchClientsResponse(List<Client> clients) {
        return new FetchClientsResponse(
                clients.stream().map(this::mapToFetchClientsResponse)
                        .collect(Collectors.toList()));
    }
    Client addRequestBodyToClient(AddClientRequest addClientRequest);

    AddClientResponse clientToAddClientResponse(Client client);
}
