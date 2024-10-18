package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.TechnicianDatabaseRepository;
import pl.servicetrack.model.Technician;
import pl.servicetrack.model.TechnicianModel;

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
}
