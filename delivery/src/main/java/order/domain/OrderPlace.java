package order.domain;

import java.util.*;
import lombok.*;
import order.domain.*;
import order.infra.AbstractEvent;

@Data
@ToString
public class OrderPlace extends AbstractEvent {

    private Long id;
}
