package pl.servicetrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.facade.Technicians;
import pl.servicetrack.controller.model.FetchTechnicianResponse;
import pl.servicetrack.controller.model.AddTechnicianRequest;
import pl.servicetrack.controller.model.AddTechnicianResponse;
import pl.servicetrack.model.Technician;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class TechnicianController {
    private final Technicians technicians;

    public TechnicianController(Technicians technicians) {
        this.technicians = technicians;
    }

    @PostMapping("/technicians")
    ResponseEntity<?> addTechnician(@RequestBody AddTechnicianRequest addTechnicianRequest) {
        if (addTechnicianRequest.isInvalid()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return technicians.addTechnician(new Technician(
                addTechnicianRequest.id(),
                addTechnicianRequest.firstName(),
                addTechnicianRequest.lastName(),
                addTechnicianRequest.email(),
                addTechnicianRequest.phoneNumber()
        )).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(CREATED).body(new AddTechnicianResponse(
                                response.id(),
                                response.firstName(),
                                response.lastName(),
                                response.email(),
                                response.phoneNumber()
                        )
                )
        );
    }

    @GetMapping("/technicians/{technician_id}")
    ResponseEntity<?> fetchTechnician(@PathVariable("technician_id") UUID technicianId) {
        if (technicianId == null || technicianId.toString().isBlank()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
        return technicians.fetchTechnician(technicianId).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(OK).body(new FetchTechnicianResponse(
                        response.id(),
                        response.firstName(),
                        response.lastName(),
                        response.email(),
                        response.phoneNumber()
                ))
        );
    }

    @DeleteMapping("/technicians/{technician_id}")
    ResponseEntity<?> deleteTechnician(@PathVariable("technician_id") UUID technicianId) {
        if (technicianId == null || technicianId.toString().isBlank()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return technicians.deleteTechnician(technicianId).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                success -> ResponseEntity.status(OK).build()
        );
    }
}
