package pl.servicetrack.controller.model;

import java.util.UUID;

public record AddTechnicianRequest(UUID id,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String phoneNumber) {
    public boolean isInvalid() {
        return firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank();
    }
}
