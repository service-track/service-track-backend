package pl.servicetrack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.model.AddClientRequest;
import pl.servicetrack.model.AddClientResponse;
import pl.servicetrack.model.FetchClientResponse;
import pl.servicetrack.model.FetchClientsResponse;
import pl.servicetrack.model.*;

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
