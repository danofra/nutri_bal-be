package danofra.nutri_balbe.entities;

import danofra.nutri_balbe.enums.Gender;
import danofra.nutri_balbe.enums.Physical_activity;
import danofra.nutri_balbe.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate date_of_birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Physical_activity physical_activity;
    private String nationality;
    private String city_of_residence;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String surname, String email, String password, LocalDate date_of_birth, Gender gender, Physical_activity physical_activity, String nationality, String city_of_residence, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.physical_activity = physical_activity;
        this.nationality = nationality;
        this.city_of_residence = city_of_residence;
        this.role = role;
    }
}
