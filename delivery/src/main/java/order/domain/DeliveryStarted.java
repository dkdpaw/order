package order.domain;

import java.util.*;
import lombok.*;
import order.domain.*;
import order.infra.AbstractEvent;

@Data
@ToString
public class DeliveryStarted extends AbstractEvent {

    private Long deliveryId;
    private Long orderId;
    private String customerId;

    public DeliveryStarted(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryStarted() {
        super();
    }
}
