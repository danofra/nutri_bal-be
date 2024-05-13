package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.Meals;
import danofra.nutri_balbe.entities.MealsQuantity;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.enums.Type_meals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MealsQuantityDAO extends JpaRepository<MealsQuantity, Integer> {
    List<MealsQuantity> findByMealsId(int id);

    Optional<MealsQuantity> findByMealsIdAndProductName(int mealsId, String productName);

    @Query("SELECT m FROM Meals m WHERE m.day = :day AND m.month = :month AND m.year = :year AND m.type_meals = :typeMeals AND m.user = :userId")
    Optional<Meals> findByDayMonthYearAndTypeMeals(@Param("day") int day,
                                                   @Param("month") int month,
                                                   @Param("year") int year,
                                                   @Param("typeMeals") Type_meals typeMeals,
                                                   @Param("userId") User userId);


}
