package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.GroceryShopping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroceryShoppingDAO extends JpaRepository<GroceryShopping, Integer> {

    Optional<GroceryShopping> findByUserId(int userId);
}
