package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.BaseError;
import pl.servicetrack.db.TechnicianRepository;
import pl.servicetrack.error.TechnicianDomainError;
import pl.servicetrack.model.Technician;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Technicians {

    private final TechnicianRepository technicianRepository;
    private final TechnicianMapper technicianMapper = TechnicianMapper.INSTANCE;

    public Technicians(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public Either<BaseError, Technician> addTechnician(Technician technician) {
        return technicianRepository.save(technicianMapper.technicianToTechnicianEntity(technician))
                .map(response -> technician);
    }

    public Either<BaseError, List<Technician>> fetchTechnicians() {
        return technicianRepository.findAll()
                .map(technicianMapper::technicianEntitiesToTechnicians);
    }

    public Either<BaseError, Technician> fetchTechnician(UUID technicianId) {
        return technicianRepository.find(technicianId)
                .filterOrElse(
                        Objects::nonNull,
                        error -> new TechnicianDomainError.TechnicianNotFound()
                )
                .map(technicianMapper::technicianEntityToTechnician);
    }

    public Either<BaseError, UUID> deleteTechnician(UUID technicianId) {
        return technicianRepository.delete(technicianId);
    }
}
