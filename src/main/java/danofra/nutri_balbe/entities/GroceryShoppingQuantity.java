package danofra.nutri_balbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "grocery_shopping_quantity")
public class GroceryShoppingQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "grocery_shopping_id")
    private GroceryShopping groceryShopping;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public GroceryShoppingQuantity(int quantity, Product product, GroceryShopping groceryShopping) {
        this.quantity = quantity;
        this.product = product;
        this.groceryShopping = groceryShopping;
    }
}
