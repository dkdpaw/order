package order.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import order.config.kafka.KafkaProcessor;
import order.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderPlace'"
    )
    public void wheneverOrderPlace_DeliveryStart(
        @Payload OrderPlace orderPlace
    ) {
        OrderPlace event = orderPlace;
        System.out.println(
            "\n\n##### listener DeliveryStart : " + orderPlace + "\n\n"
        );

        // Sample Logic //
        Delivery.deliveryStart(event);
    }
}
