package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.FoodStorageQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodStorageQuantityDAO extends JpaRepository<FoodStorageQuantity, Integer> {

    List<FoodStorageQuantity> findByFoodStorageId(int id);

    Page<FoodStorageQuantity> findByFoodStorageId(int foodStorageId, Pageable pageable);

    Optional<FoodStorageQuantity> findByFoodStorageIdAndProductName(int foodStorageId, String productName);
}
