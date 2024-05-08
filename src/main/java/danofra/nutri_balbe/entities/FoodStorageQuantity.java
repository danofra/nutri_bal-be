package danofra.nutri_balbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "food_storage_id")
    private FoodStorage foodStorage;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public FoodStorageQuantity(int quantity, Product product, FoodStorage foodStorage) {
        this.quantity = quantity;
        this.product = product;
        this.foodStorage = foodStorage;
    }
}
