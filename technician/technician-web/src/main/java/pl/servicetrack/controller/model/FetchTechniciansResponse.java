package pl.servicetrack.controller.model;

import java.util.List;
import java.util.UUID;

public record FetchTechniciansResponse(List<Technician> technicians) {
    public record Technician(UUID id,
                             String firstName,
                             String lastName,
                             String email,
                             String phoneNumber) {

    }
}
