package pl.servicetrack.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.controller.mapper.ClientControllerMapper;
import pl.servicetrack.controller.model.AddClientRequest;
import pl.servicetrack.facade.Clients;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

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
        if (addClientResponse.IsInvalid()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return clients.addClient(
                        clientControllerMapper.addRequestBodyToClient(addClientResponse))
                .fold(
                        error -> ResponseEntity.status(CONFLICT).build(),
                        response -> ResponseEntity.status(CREATED).body(
                                clientControllerMapper.clientToAddClientResponse(response)
                        )
                );
    }

    @GetMapping("/clients/{client_id}")
    ResponseEntity<?> fetchClient(@PathVariable("client_id") UUID clientId) {
        if (clientId == null || clientId.toString().isBlank()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return clients.fetchClient(clientId).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(OK).body(
                        clientControllerMapper.clientToFetchClientResponse(response)
                )
        );
    }

    @DeleteMapping("/clients/{client_id}")
    ResponseEntity<?> deleteClient(@PathVariable("client_id") UUID client_id) {
        if (client_id == null || client_id.toString().isBlank()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return clients.deleteClient(client_id).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                success -> ResponseEntity.status(OK).build()
        );
    }
}
