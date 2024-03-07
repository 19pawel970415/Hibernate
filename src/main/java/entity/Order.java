package entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity (name = "orders")
public class Order  {

    @EmbeddedId
    private OrderId orderId;

    private String orderNumber;

    @ManyToOne
    Client client;
}
