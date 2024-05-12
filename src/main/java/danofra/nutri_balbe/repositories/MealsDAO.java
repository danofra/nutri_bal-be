package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.Meals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealsDAO extends JpaRepository<Meals, Integer> {
    Optional<Meals> findByUserId(int userId);
}
