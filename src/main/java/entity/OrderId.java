package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class OrderId implements Serializable {
    private Character orderingLetter;
    private int checkDigit;
}
