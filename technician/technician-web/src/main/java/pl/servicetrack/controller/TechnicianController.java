package pl.servicetrack.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.controller.model.AddTechnicianRequest;
import pl.servicetrack.controller.mapper.TechnicianControllerMapper;
import pl.servicetrack.facade.Technicians;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class TechnicianController {
    private final TechnicianControllerMapper technicianControllerMapper = TechnicianControllerMapper.INSTANCE;
    private final Technicians technicians;

    public TechnicianController(Technicians technicians) {
        this.technicians = technicians;
    }

    @GetMapping("/technicians")
    ResponseEntity<?> fetchTechnicians() {
        return technicians.fetchTechnicians().fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(OK).body(
                        technicianControllerMapper.techniciansToFetchTechniciansResponse(response)
                )
        );
    }

    @PostMapping("/technicians")
    ResponseEntity<?> addTechnician(@Valid @RequestBody AddTechnicianRequest addTechnicianRequest) {
        if (addTechnicianRequest.isInvalid()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        return technicians.addTechnician(
                        technicianControllerMapper.addRequestBodyToTechnician(addTechnicianRequest))
                .fold(
                        error -> ResponseEntity.status(CONFLICT).build(),
                        response -> ResponseEntity.status(CREATED).body(
                                technicianControllerMapper.technicianToAddTechnicianResponse(response))
                );
    }

    @GetMapping("/technicians/{technician_id}")
    ResponseEntity<?> fetchTechnician(@PathVariable("technician_id") UUID technicianId) {
        if (technicianId == null || technicianId.toString().isBlank()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
        return technicians.fetchTechnician(technicianId).fold(
                error -> ResponseEntity.status(CONFLICT).build(),
                response -> ResponseEntity.status(OK).body(
                        technicianControllerMapper.technicianToFetchTechnicianResponse(response)
                )
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
