package danofra.nutri_balbe.repositories;

import danofra.nutri_balbe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDAO extends JpaRepository<Product, String> {
    boolean existsByName(String name);

    Optional<Product> findByName(String name);
}
