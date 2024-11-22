package pl.servicetrack;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.servicetrack.facade.ServiceOrders;
import pl.servicetrack.model.ServiceOrderControllerMapper;
import pl.servicetrack.model.CreateServiceOrderRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin("*")
@RestController
public class ServiceOrderController {
    private final ServiceOrderControllerMapper serviceOrderControllerMapper = ServiceOrderControllerMapper.INSTANCE;
    private final ServiceOrders serviceOrders;

    public ServiceOrderController(ServiceOrders serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    @GetMapping("/serviceorders")
    ResponseEntity<?> fetchServiceOrders() {
        return serviceOrders.fetchServiceOrders()
                .fold(
                        ServiceOrderResponseResolver::resolveError,
                        response -> ResponseEntity.status(OK).body(
                                serviceOrderControllerMapper.serviceOrdersToFetchServiceOrdersResponse(response)
                        )
                );
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
                        ServiceOrderResponseResolver::resolveError,
                        response -> ResponseEntity.status(CREATED).body(
                                serviceOrderControllerMapper.serviceOrderToCreateServiceOrderResponse(response)
                        )
                );
    }

    @GetMapping("/serviceorders/{serviceOrderId}")
    ResponseEntity<?> fetchServiceOrder(@PathVariable("serviceOrderId") UUID serviceOrderId) {
        return serviceOrders.fetchServiceOrder(serviceOrderId)
                .fold(
                        ServiceOrderResponseResolver::resolveError,
                        response -> ResponseEntity.status(OK).body(
                                serviceOrderControllerMapper.serviceOrderToFetchServiceOrderResponse(response)
                        )
                );
    }

    @DeleteMapping("/serviceorders/{serviceOrderId}")
    ResponseEntity<?> deleteServiceOrder(@PathVariable("serviceOrderId") UUID serviceOrderId) {
        return serviceOrders.deleteServiceOrder(serviceOrderId)
                .fold(
                        ServiceOrderResponseResolver::resolveError,
                        success -> ResponseEntity.status(OK).build()
                );
    }
}
