package pl.servicetrack.controller.model;

import java.util.UUID;

public record AddClientRequest(UUID id,
                               String name,
                               String email,
                               String phoneNumber){
    public boolean IsInvalid(){
        return name.isBlank() || phoneNumber.isBlank();
    }
}
