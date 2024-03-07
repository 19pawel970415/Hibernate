package entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity (name = "orders")
public class Order  {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String orderNumber;

    @ManyToOne
    Client client;
}
