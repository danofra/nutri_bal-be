package danofra.nutri_balbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "food_storage")
public class FoodStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "foodStorage")
    private List<FoodStorageQuantity> foodStorageQuantity;

    public FoodStorage(User user) {
        this.user = user;
    }
}
