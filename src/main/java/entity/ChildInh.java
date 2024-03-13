package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "children")
@Entity
public class ChildInh extends PersonInh {

    private String schoolName;

    @ManyToMany (mappedBy = "children")
    private List<ParentInh> parents;

    public ChildInh(String schoolName, List<ParentInh> parents) {
        this.schoolName = schoolName;
        this.parents = parents;
    }
}
