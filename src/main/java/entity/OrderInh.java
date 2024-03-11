package entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class OrderInh {

    @EmbeddedId
    private OrderId orderId;

    private String orderNumber;

    @ManyToOne
    ClientInh client;

    public OrderInh(OrderId orderId, String orderNumber, ClientInh client) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.client = client;
    }
}
