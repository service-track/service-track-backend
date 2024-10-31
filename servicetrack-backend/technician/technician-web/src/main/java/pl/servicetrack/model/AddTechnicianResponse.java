package pl.servicetrack.model;

import java.util.UUID;

public record AddTechnicianResponse(UUID id,
                                    String firstName,
                                    String lastName,
                                    String email,
                                    String phoneNumber) {
}
