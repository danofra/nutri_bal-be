package danofra.nutri_balbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int kcal;
    private String image;
    @OneToMany(mappedBy = "product")
    private List<FoodStorageQuantity> food_storage_quantity;

    public Product(String name, String description, int kcal, String image) {
        this.name = name;
        this.description = description;
        this.kcal = kcal;
        this.image = image;
    }

}