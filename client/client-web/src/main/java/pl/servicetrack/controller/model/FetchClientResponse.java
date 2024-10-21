package pl.servicetrack.controller.model;

import java.util.UUID;

public record FetchClientResponse(UUID id,
                                  String name,
                                  String email,
                                  String phoneNumber) {
}
