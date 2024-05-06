package danofra.nutri_balbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "food_storage_quantity")
public class FoodStorageQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "food_storage_id")
    private FoodStorage food_storage;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public FoodStorageQuantity(int quantity, Product product, FoodStorage food_storage) {
        this.quantity = quantity;
        this.product = product;
        this.food_storage = food_storage;
    }
}
