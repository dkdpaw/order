package order.domain;

import java.util.*;
import lombok.*;
import order.domain.*;
import order.infra.AbstractEvent;

@Data
@ToString
public class DeliveryCompleted extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String productName;
    private String productId;
    private Long customerId;
    private Integer qty;
    private String status;

    public DeliveryCompleted(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryCompleted() {
        super();
    }
}
