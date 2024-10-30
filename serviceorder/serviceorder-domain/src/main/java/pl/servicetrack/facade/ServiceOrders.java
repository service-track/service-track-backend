package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.ServiceOrderDatabaseRepository;
import pl.servicetrack.model.ServiceOrder;

import java.util.Objects;
import java.util.UUID;

public class ServiceOrders {
    private static final String FAILED_TO_FIND_SERVICEORDER = "Failed to find serviceOrder!";
    private final ServiceOrderDatabaseRepository serviceOrderDatabaseRepository;
    private final ServiceOrderMapper serviceOrderMapper = ServiceOrderMapper.INSTANCE;

    public ServiceOrders(ServiceOrderDatabaseRepository serviceOrderDatabaseRepository) {
        this.serviceOrderDatabaseRepository = serviceOrderDatabaseRepository;
    }

    public Either<Error, ServiceOrder> createServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderDatabaseRepository.save(serviceOrderMapper.serviceOrderToServiceOrderEntity(serviceOrder))
                .map(response -> serviceOrder);
    }

    public Either<Error, ServiceOrder> fetchServiceOrder(UUID serviceOrderId) {
        return serviceOrderDatabaseRepository.find(serviceOrderId)
                .filterOrElse(Objects::nonNull, error -> new Error(FAILED_TO_FIND_SERVICEORDER))
                .map(serviceOrderMapper::serviceOrderEntityToServiceOrder);
    }
}
