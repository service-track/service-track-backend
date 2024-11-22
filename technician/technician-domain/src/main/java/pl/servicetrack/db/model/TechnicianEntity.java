package pl.servicetrack.db.model;

import java.util.UUID;

public record TechnicianEntity(UUID id,
                               String firstName,
                               String lastName,
                               String email,
                               String phoneNumber){

}
