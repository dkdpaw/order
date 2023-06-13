package order.domain;

import java.util.*;
import lombok.*;
import order.domain.*;
import order.infra.AbstractEvent;

@Data
@ToString
public class StockIncrease extends AbstractEvent {

    private Long id;
    private String productName;
    private Integer stock;
    private String productId;

    public StockIncrease(Stock aggregate) {
        super(aggregate);
    }

    public StockIncrease() {
        super();
    }
}
