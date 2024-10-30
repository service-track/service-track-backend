package pl.servicetrack;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.facade.ServiceOrders;
import pl.servicetrack.mapper.ServiceOrderControllerMapper;
import pl.servicetrack.model.CreateServiceOrderRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class ServiceOrderController {
    private final ServiceOrderControllerMapper serviceOrderControllerMapper = ServiceOrderControllerMapper.INSTANCE;
    private final ServiceOrders serviceOrders;

    public ServiceOrderController(ServiceOrders serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    @PostMapping("/serviceorders")
    ResponseEntity<?> createServiceOrder(@Valid @RequestBody CreateServiceOrderRequest createServiceOrderRequest) {
        return serviceOrders.createServiceOrder(
                        serviceOrderControllerMapper.createRequestBodyToServiceOrder(
                                createServiceOrderRequest,
                                UUID.randomUUID(),
                                UUID.randomUUID(),
                                LocalDateTime.now()))
                .fold(
                        error -> ResponseEntity.status(CONFLICT).build(),
                        response -> ResponseEntity.status(CREATED).body(
                                serviceOrderControllerMapper.serviceOrderToCreateServiceOrderResponse(response)
                        )
                );
    }

    @GetMapping("/serviceorders/{serviceOrderId}")
    ResponseEntity<?> fetchServiceOrder(@PathVariable("serviceOrderId") UUID serviceOrderId) {
        return serviceOrders.fetchServiceOrder(serviceOrderId)
                .fold(
                        error -> ResponseEntity.status(CONFLICT).build(),
                        response -> ResponseEntity.status(OK).body(
                                serviceOrderControllerMapper.serviceOrderToFetchServiceOrderResponse(response)
                        )
                );
    }

    @DeleteMapping("/serviceorders/{serviceOrderId}")
    ResponseEntity<?> deleteServiceOrder(@PathVariable("serviceOrderId") UUID serviceOrderId) {
        return serviceOrders.deleteServiceOrder(serviceOrderId)
                .fold(
                        error -> ResponseEntity.status(CONFLICT).build(),
                        success -> ResponseEntity.status(OK).build()
                );
    }
}
