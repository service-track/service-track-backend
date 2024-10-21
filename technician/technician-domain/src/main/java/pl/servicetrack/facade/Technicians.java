package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.TechnicianDatabaseRepository;
import pl.servicetrack.controller.model.Technician;
import pl.servicetrack.controller.model.TechnicianModel;

import java.util.UUID;

public class Technicians {
    private final TechnicianDatabaseRepository technicianDatabaseRepository;

    public Technicians(TechnicianDatabaseRepository technicianDatabaseRepository) {
        this.technicianDatabaseRepository = technicianDatabaseRepository;
    }

    public Either<Error, Technician> addTechnician(Technician technician) {
        return technicianDatabaseRepository.save(new TechnicianModel(
                technician.id(),
                technician.firstName(),
                technician.lastName(),
                technician.email(),
                technician.phoneNumber()
        )).map(response -> technician);
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
