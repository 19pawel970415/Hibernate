package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity (name = "parent")
public class Parent {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String workplaceName;

    @ManyToMany
    private List<Child> children;

    @OneToOne
    private Person person;

    public Parent(String workplaceName, List<Child> children, Person person) {
        this.workplaceName = workplaceName;
        this.children = children;
        this.person = person;
    }
}
