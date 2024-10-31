package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.ServiceOrderDatabaseRepository;
import pl.servicetrack.model.ServiceOrder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ServiceOrders {
    private static final String SERVICEORDER_NOT_FOUND = "Service order hasn't been found!";
    private final ServiceOrderDatabaseRepository serviceOrderDatabaseRepository;
    private final ServiceOrderMapper serviceOrderMapper = ServiceOrderMapper.INSTANCE;

    public ServiceOrders(ServiceOrderDatabaseRepository serviceOrderDatabaseRepository) {
        this.serviceOrderDatabaseRepository = serviceOrderDatabaseRepository;
    }

    public Either<Error, ServiceOrder> createServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderDatabaseRepository.save(serviceOrderMapper.serviceOrderToServiceOrderEntity(serviceOrder))
                .map(response -> serviceOrder);
    }

    public Either<Error, List<ServiceOrder>> fetchServiceOrders() {
        return serviceOrderDatabaseRepository.findAll()
                .map(serviceOrderMapper::serviceOrderEntitiesToServiceOrders);
    }

    public Either<Error, ServiceOrder> fetchServiceOrder(UUID serviceOrderId) {
        return serviceOrderDatabaseRepository.find(serviceOrderId)
                .filterOrElse(Objects::nonNull, error -> new Error(SERVICEORDER_NOT_FOUND))
                .map(serviceOrderMapper::serviceOrderEntityToServiceOrder);
    }

    public Either<Error, Integer> deleteServiceOrder(UUID serviceOrderId) {
        return serviceOrderDatabaseRepository.delete(serviceOrderId)
                .filterOrElse(Objects::nonNull, error -> new Error(SERVICEORDER_NOT_FOUND));
    }
}
