package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.GroceryShoppingQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroceryShoppingQuantityDAO extends JpaRepository<GroceryShoppingQuantity, Integer> {

    List<GroceryShoppingQuantity> findByGroceryShoppingId(int id);

    Page<GroceryShoppingQuantity> findByGroceryShoppingId(int groceryShoppingId, Pageable pageable);

    Optional<GroceryShoppingQuantity> findByGroceryShoppingIdAndProductId(int groceryShoppingId, int productId);
}
