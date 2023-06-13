package order.domain;

import java.util.*;
import lombok.*;
import order.domain.*;
import order.infra.AbstractEvent;

@Data
@ToString
public class StockDecreased extends AbstractEvent {

    private Long id;
    private String productName;
    private Integer stock;
    private String productId;

    public StockDecreased(Stock aggregate) {
        super(aggregate);
    }

    public StockDecreased() {
        super();
    }
}
