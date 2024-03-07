package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity (name = "child")
public class Child {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String schoolName;

    @ManyToMany(mappedBy = "children", cascade = CascadeType.ALL)
    private List<Parent> parents;

    @OneToOne
    private Person person;

    public Child(String schoolName, List<Parent> parents, Person person) {
        this.schoolName = schoolName;
        this.parents = parents;
        this.person = person;
    }
}
