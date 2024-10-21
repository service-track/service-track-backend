package pl.servicetrack.model;

import java.util.UUID;

public record ClientModel(UUID id,
                                String name,
                                String email,
                                String phoneNumber) {
}