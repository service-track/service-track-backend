package pl.servicetrack.facade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.servicetrack.entity.ServiceOrderEntity;
import pl.servicetrack.model.ServiceOrder;

@Mapper
public interface ServiceOrderMapper {
    ServiceOrderMapper INSTANCE = Mappers.getMapper(ServiceOrderMapper.class);

    ServiceOrderEntity serviceOrderToServiceOrderEntity(ServiceOrder serviceOrder);
}
