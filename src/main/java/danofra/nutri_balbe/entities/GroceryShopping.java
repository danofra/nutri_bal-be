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
@Table(name = "grocery_shopping")
public class GroceryShopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public GroceryShopping(User user) {
        this.user = user;
    }
}
