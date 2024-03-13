package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "clients")
@Entity
public class ClientInh extends PersonInh {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderInh> orders;

    public ClientInh(List<OrderInh> orders) {
        this.orders = orders;
    }
}
