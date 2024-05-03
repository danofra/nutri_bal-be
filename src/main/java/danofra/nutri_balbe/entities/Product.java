package danofra.nutri_balbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Product(String name, String description, int kcal, String image) {
        this.name = name;
        this.description = description;
        this.kcal = kcal;
        this.image = image;
    }

}