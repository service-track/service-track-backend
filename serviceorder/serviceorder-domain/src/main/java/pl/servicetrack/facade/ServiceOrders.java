package pl.servicetrack.facade;

import io.vavr.control.Either;
import pl.servicetrack.db.ServiceOrderDatabaseRepository;
import pl.servicetrack.model.ServiceOrder;

public class ServiceOrders {
    private final ServiceOrderDatabaseRepository serviceOrderDatabaseRepository;
    private final ServiceOrderMapper serviceOrderMapper = ServiceOrderMapper.INSTANCE;

    public ServiceOrders(ServiceOrderDatabaseRepository serviceOrderDatabaseRepository) {
        this.serviceOrderDatabaseRepository = serviceOrderDatabaseRepository;
    }

    public Either<Error, ServiceOrder> createServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderDatabaseRepository.save(serviceOrderMapper.serviceOrderToServiceOrderEntity(serviceOrder))
                .map(response -> serviceOrder);
    }
}
