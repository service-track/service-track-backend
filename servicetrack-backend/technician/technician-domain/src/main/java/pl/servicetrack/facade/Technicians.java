package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.TechnicianDatabaseRepository;
import pl.servicetrack.model.Technician;

import java.util.List;
import java.util.UUID;

public class Technicians {
    private final TechnicianDatabaseRepository technicianDatabaseRepository;
    private final TechnicianMapper technicianMapper = TechnicianMapper.INSTANCE;

    public Technicians(TechnicianDatabaseRepository technicianDatabaseRepository) {
        this.technicianDatabaseRepository = technicianDatabaseRepository;
    }

    public Either<Error, Technician> addTechnician(Technician technician) {
        return technicianDatabaseRepository.save(technicianMapper.technicianToTechnicianEntity(technician))
                .map(response -> technician);
    }

    public Either<Error, List<Technician>> fetchTechnicians() {
        return technicianDatabaseRepository.findAll()
                .map(technicianMapper::technicianEntitiesToTechnicians);
    }

    public Either<Error, Technician> fetchTechnician(UUID technicianId) {
        return technicianDatabaseRepository.find(technicianId)
                .map(technicianMapper::technicianEntityToTechnician);
    }

    public Either<Error, Integer> deleteTechnician(UUID technicianId) {
        return technicianDatabaseRepository.delete(technicianId);
    }
}
