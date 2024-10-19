package pl.servicetrack.controller.model;

import java.util.UUID;

public record FetchTechnicianResponse(UUID id,
                                      String firstName,
                                      String lastName,
                                      String email,
                                      String phoneNumber) {
}
