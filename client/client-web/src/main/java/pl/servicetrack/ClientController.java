package pl.servicetrack;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.mapper.ClientControllerMapper;
import pl.servicetrack.model.AddClientRequest;
import pl.servicetrack.facade.Clients;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin("*")
@RestController
public class ClientController {
    private final Clients clients;
    private final ClientControllerMapper clientControllerMapper = ClientControllerMapper.INSTANCE;

    public ClientController(Clients clients) {
        this.clients = clients;
    }

    @GetMapping("/clients")
    ResponseEntity<?> fetchClients() {
        return clients.fetchClients().fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(OK).body(
                        clientControllerMapper.clientsToFetchClientsResponse(response)
                )
        );
    }

    @PostMapping("/clients")
    ResponseEntity<?> addClient(@Valid @RequestBody AddClientRequest addClientResponse) {
        return clients.addClient(clientControllerMapper.addRequestBodyToClient(addClientResponse))
                .fold(
                        error -> ResponseEntity.status(CONFLICT).build(),
                        response -> ResponseEntity.status(CREATED).body(
                                clientControllerMapper.clientToAddClientResponse(response)
                        )
                );
    }

    @GetMapping("/clients/{clientId}")
    ResponseEntity<?> fetchClient(@PathVariable("clientId") UUID clientId) {
        return clients.fetchClient(clientId).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(OK).body(
                        clientControllerMapper.clientToFetchClientResponse(response)
                )
        );
    }

    @DeleteMapping("/clients/{clientId}")
    ResponseEntity<?> deleteClient(@PathVariable("clientId") UUID client_id) {
        return clients.deleteClient(client_id).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                success -> ResponseEntity.status(OK).build()
        );
    }
}
