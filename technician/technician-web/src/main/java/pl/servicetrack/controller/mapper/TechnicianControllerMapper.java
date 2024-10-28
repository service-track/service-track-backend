package pl.servicetrack.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.controller.model.AddTechnicianRequest;
import pl.servicetrack.controller.model.AddTechnicianResponse;
import pl.servicetrack.controller.model.FetchTechnicianResponse;
import pl.servicetrack.controller.model.FetchTechniciansResponse;
import pl.servicetrack.model.Technician;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TechnicianControllerMapper {
    TechnicianControllerMapper INSTANCE = Mappers.getMapper(TechnicianControllerMapper.class);

    Technician addRequestBodyToTechnician(AddTechnicianRequest addTechnicianRequest);

    AddTechnicianResponse technicianToAddTechnicianResponse(Technician technician);

    FetchTechnicianResponse technicianToFetchTechnicianResponse(Technician technician);

    FetchTechniciansResponse.Technician mapToFetchTechnicians(Technician technician);

    default FetchTechniciansResponse techniciansToFetchTechniciansResponse(List<Technician> technicians) {
        return new FetchTechniciansResponse(
                technicians.stream().map(this::mapToFetchTechnicians)
                        .collect(Collectors.toList()));
    }
}
