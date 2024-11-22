package pl.servicetrack.db;

import io.vavr.control.Either;
import pl.servicetrack.BaseError;
import pl.servicetrack.db.model.ServiceOrderEntity;

import java.util.List;
import java.util.UUID;

public interface ServiceOrderRepository {

    Either<BaseError, ServiceOrderEntity> save(ServiceOrderEntity serviceOrderEntity);
    Either<BaseError, List<ServiceOrderEntity>> findAll();
    Either<BaseError, ServiceOrderEntity> find(UUID serviceOrderId);
    Either<BaseError, UUID> delete(UUID serviceOrderId);
}

