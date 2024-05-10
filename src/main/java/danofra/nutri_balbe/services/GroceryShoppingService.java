package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.GroceryShopping;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.GroceryShoppingResponseDTO;
import danofra.nutri_balbe.repositories.GroceryShoppingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroceryShoppingService {
    @Autowired
    private GroceryShoppingDAO groceryShoppingDAO;

    public GroceryShoppingResponseDTO save(User userId) {
        GroceryShopping groceryShopping = new GroceryShopping(userId);
        if (groceryShoppingDAO.findByUserId(userId.getId()).isEmpty()) {
            groceryShoppingDAO.save(groceryShopping);
        }
        return new GroceryShoppingResponseDTO(userId.getName(), userId.getSurname(), groceryShopping.getId());
    }

    public GroceryShopping findByUserId(int userId) {
        return groceryShoppingDAO.findByUserId(userId).orElseThrow(() -> new BadRequestException("GroceryShopping whit user " + userId + " does not exist!"));
    }
}
