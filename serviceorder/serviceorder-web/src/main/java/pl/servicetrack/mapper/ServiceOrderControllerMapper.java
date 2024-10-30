package pl.servicetrack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface ServiceOrderControllerMapper {
    ServiceOrderControllerMapper INSTANCE = Mappers.getMapper(ServiceOrderControllerMapper.class);

    FetchServiceOrdersResponse.ServiceOrder mapToFetchServiceOrdersResponse(ServiceOrder serviceOrder);

    default FetchServiceOrdersResponse serviceOrdersToFetchServiceOrdersResponse(List<ServiceOrder> serviceOrders) {
        return new FetchServiceOrdersResponse(serviceOrders.stream()
                .map(this::mapToFetchServiceOrdersResponse)
                .collect(Collectors.toList()));
    }

    FetchServiceOrderResponse serviceOrderToFetchServiceOrderResponse(ServiceOrder serviceOrder);

    ServiceOrder createRequestBodyToServiceOrder(
            CreateServiceOrderRequest createServiceOrderRequest,
            UUID technicianId,
            UUID clientId,
            LocalDateTime creationDateTime);

    CreateServiceOrderResponse serviceOrderToCreateServiceOrderResponse(ServiceOrder serviceOrder);
}
