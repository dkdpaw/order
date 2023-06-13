package order.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import order.ProductApplication;
import order.domain.StockDecreased;
import order.domain.StockIncrease;

@Entity
@Table(name = "Stock_table")
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    private Integer stock;

    private String productId;

    @PostPersist
    public void onPostPersist() {
        StockDecreased stockDecreased = new StockDecreased(this);
        stockDecreased.publishAfterCommit();

        StockIncrease stockIncrease = new StockIncrease(this);
        stockIncrease.publishAfterCommit();
    }

    public static StockRepository repository() {
        StockRepository stockRepository = ProductApplication.applicationContext.getBean(
            StockRepository.class
        );
        return stockRepository;
    }

    public static void stockDecrease(DeliveryCompleted deliveryCompleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Stock stock = new Stock();
        repository().save(stock);

        */

        /** Example 2:  finding and process
        
        repository().findById(deliveryCompleted.get???()).ifPresent(stock->{
            
            stock // do something
            repository().save(stock);


         });
        */

    }

    public static void stockIncrease(DeliveryCompleted deliveryCompleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Stock stock = new Stock();
        repository().save(stock);

        */

        /** Example 2:  finding and process
        
        repository().findById(deliveryCompleted.get???()).ifPresent(stock->{
            
            stock // do something
            repository().save(stock);


         });
        */

    }
}
