package order.external;

import java.util.Date;
import lombok.Data;

@Data
public class Order {

    private Long orderId;
    private String productId;
    private String customerId;
    private Integer qty;
}
