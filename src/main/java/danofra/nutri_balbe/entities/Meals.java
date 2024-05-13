package danofra.nutri_balbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import danofra.nutri_balbe.enums.Type_meals;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "meals")
public class Meals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Type_meals type_meals;
    private int day;
    private int month;
    private int year;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "meals")
    private List<MealsQuantity> mealsQuantity;

    public Meals(Type_meals type_meals, int day, int month, int year, User user) {
        this.type_meals = type_meals;
        this.day = day;
        this.month = month;
        this.year = year;
        this.user = user;
    }
}
