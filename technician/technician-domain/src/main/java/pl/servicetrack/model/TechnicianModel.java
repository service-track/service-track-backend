package pl.servicetrack.model;

import java.util.UUID;

public record TechnicianModel (UUID id,
                               String firstName,
                               String lastName,
                               String email,
                               String phoneNumber){

}
