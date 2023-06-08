package order.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import order.OrderApplication;
import order.domain.OrderPlace;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    private String productId;

    private Long customerId;

    private Integer qty;

    @PostPersist
    public void onPostPersist() {
        OrderPlace orderPlace = new OrderPlace(this);
        orderPlace.publishAfterCommit();
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
