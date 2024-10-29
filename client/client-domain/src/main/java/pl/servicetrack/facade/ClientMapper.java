package pl.servicetrack.facade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.model.Client;
import pl.servicetrack.model.ClientEntity;

import java.util.List;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientEntity clientToClientEntity(Client client);

    Client clientEntityToClient(ClientEntity clientEntity);

    List<Client> clientEntitiesToClients(List<ClientEntity> clientEntities);
}
