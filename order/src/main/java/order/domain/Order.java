package order.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import order.OrderApplication;
import order.domain.OrderCanceled;
import order.domain.OrderPlaced;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private String productId;

    private String customerId;

    private Integer qty;

    @PostPersist
    public void onPostPersist() {
        OrderCanceled orderCanceled = new OrderCanceled(this);
        orderCanceled.publishAfterCommit();
        // Get request from Order
        //order.external.Order order =
        //    Application.applicationContext.getBean(order.external.OrderService.class)
        //    .getOrder(/** mapping value needed */);

    }

    @PrePersist
    public void onPrePersist() {
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
