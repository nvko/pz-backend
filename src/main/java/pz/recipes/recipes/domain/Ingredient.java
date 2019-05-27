package pz.recipes.recipes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean vege;

    public Ingredient(@NotEmpty String name, Boolean vege) {
        this.name = name;
        this.vege = vege;
    }

    @OneToMany(mappedBy = "", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<Fridge> fridge;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVege() {
        return vege;
    }

    public void setVege(Boolean vege) {
        this.vege = vege;
    }

    public Set<Fridge> getFridge() {
        return fridge;
    }

    public void setFridge(Set<Fridge> fridge) {
        this.fridge = fridge;
    }
}
