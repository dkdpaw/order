package order.domain;

import java.util.*;
import lombok.*;
import order.domain.*;
import order.infra.AbstractEvent;

@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long orderId;
    private String productId;
    private String customerId;
    private Integer qty;
    private String productName;
}
