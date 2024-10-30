package pl.servicetrack.entity;

import java.util.UUID;

public record ClientEntity(UUID id,
                           String name,
                           String email,
                           String phoneNumber) {
}