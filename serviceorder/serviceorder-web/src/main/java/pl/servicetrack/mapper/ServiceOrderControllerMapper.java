package pl.servicetrack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.model.CreateServiceOrderRequest;
import pl.servicetrack.model.CreateServiceOrderResponse;
import pl.servicetrack.model.FetchServiceOrderResponse;
import pl.servicetrack.model.ServiceOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper
public interface ServiceOrderControllerMapper {
    ServiceOrderControllerMapper INSTANCE = Mappers.getMapper(ServiceOrderControllerMapper.class);

    ServiceOrder createRequestBodyToServiceOrder(
            CreateServiceOrderRequest createServiceOrderRequest,
            UUID technicianId,
            UUID clientId,
            LocalDateTime creationDateTime);

    CreateServiceOrderResponse serviceOrderToCreateServiceOrderResponse(ServiceOrder serviceOrder);

    FetchServiceOrderResponse serviceOrderToFetchServiceOrderResponse(ServiceOrder serviceOrder);
}
