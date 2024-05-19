package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM MealsQuantity mq WHERE mq.meals.id IN (SELECT m.id FROM Meals m WHERE m.user.id = :userId)")
    void deleteMealsQuantitiesByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Meals m WHERE m.user.id = :userId")
    void deleteMealsByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FoodStorageQuantity fsq WHERE fsq.foodStorage.id IN (SELECT fs.id FROM FoodStorage fs WHERE fs.user.id = :userId)")
    void deleteFoodStorageQuantitiesByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FoodStorage fs WHERE fs.user.id = :userId")
    void deleteFoodStorageByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroceryShoppingQuantity gsq WHERE gsq.groceryShopping.id IN (SELECT gs.id FROM GroceryShopping gs WHERE gs.user.id = :userId)")
    void deleteGroceryShoppingQuantitiesByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroceryShopping gs WHERE gs.user.id = :userId")
    void deleteGroceryShoppingByUserId(int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :userId")
    void deleteUserById(int userId);
}
