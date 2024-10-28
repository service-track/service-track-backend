package pl.servicetrack.model;

import java.util.UUID;

public record ClientEntity(UUID id,
                           String name,
                           String email,
                           String phoneNumber) {
}