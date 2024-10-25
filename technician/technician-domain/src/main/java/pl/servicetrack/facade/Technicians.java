package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.TechnicianDatabaseRepository;
import pl.servicetrack.etities.TechnicianEntity;
import pl.servicetrack.model.Technician;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Technicians {
    private final TechnicianDatabaseRepository technicianDatabaseRepository;

    public Technicians(TechnicianDatabaseRepository technicianDatabaseRepository) {
        this.technicianDatabaseRepository = technicianDatabaseRepository;
    }

    public Either<Error, Technician> addTechnician(Technician technician) {
        return technicianDatabaseRepository.save(new TechnicianEntity(
                technician.id(),
                technician.firstName(),
                technician.lastName(),
                technician.email(),
                technician.phoneNumber()
        )).map(response -> technician);
    }

    public Either<Error, List<Technician>> fetchTechnicians() {
        return technicianDatabaseRepository.findAll()
                .map(response -> response.stream()
                        .map(technicianEntity -> new Technician(
                                technicianEntity.id(),
                                technicianEntity.firstName(),
                                technicianEntity.lastName(),
                                technicianEntity.email(),
                                technicianEntity.phoneNumber()
                        )).collect(Collectors.toList())
                );
    }

    public Either<Error, Technician> fetchTechnician(UUID technicianId) {
        return technicianDatabaseRepository.find(technicianId)
                .map(response -> new Technician(
                        response.id(),
                        response.firstName(),
                        response.lastName(),
                        response.email(),
                        response.phoneNumber()
                ));
    }

    public Either<Error, Integer> deleteTechnician(UUID technicianId) {
        return technicianDatabaseRepository.delete(technicianId);
    }
}
