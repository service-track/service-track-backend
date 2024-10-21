package pl.servicetrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.controller.model.FetchClientResponse;
import pl.servicetrack.facade.Clients;
import pl.servicetrack.controller.model.AddClientRequest;
import pl.servicetrack.controller.model.AddClientResponse;
import pl.servicetrack.model.Client;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class ClientController {
    private final Clients clients;

    public ClientController(Clients clients) {
        this.clients = clients;
    }

    @PostMapping("/clients")
    ResponseEntity<?> addClient(@RequestBody AddClientRequest addClientResponse) {
        if (addClientResponse.IsInvalid()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return clients.addClient(new Client(
                addClientResponse.id(),
                addClientResponse.name(),
                addClientResponse.email(),
                addClientResponse.phoneNumber()
        )).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(CREATED).body(new AddClientResponse(
                                response.id(),
                                response.name(),
                                response.email(),
                                response.phoneNumber()
                        )
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
                response -> ResponseEntity.status(OK).body(new FetchClientResponse(
                        response.id(),
                        response.name(),
                        response.email(),
                        response.phoneNumber()
                ))
        );
    }


}
