package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.FoodStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodStorageDAO extends JpaRepository<FoodStorage, Integer> {
    Optional<FoodStorage> findByUserId(int userId);
}
