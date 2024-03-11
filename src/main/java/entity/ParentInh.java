package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@Entity
public class ParentInh extends PersonInh {

    private String workplaceName;

    @ManyToMany
    private List<ChildInh> children;

    public ParentInh(String workplaceName, List<ChildInh> children) {
        this.workplaceName = workplaceName;
        this.children = children;
    }
}
