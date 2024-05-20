package danofra.nutri_balbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import danofra.nutri_balbe.enums.Unit_of_measure;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "meals_quantity")
public class MealsQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_name")
    private Product product;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "meals_id")
    private Meals meals;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private Unit_of_measure unit_of_measure;


    public MealsQuantity(int quantity, Unit_of_measure unit_of_measure, Product product, Meals meals) {
        this.quantity = quantity;
        this.unit_of_measure = unit_of_measure;
        this.product = product;
        this.meals = meals;
    }
}
