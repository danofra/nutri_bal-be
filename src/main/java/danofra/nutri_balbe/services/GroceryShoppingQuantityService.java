package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.GroceryShopping;
import danofra.nutri_balbe.entities.GroceryShoppingQuantity;
import danofra.nutri_balbe.entities.Product;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.GroceryShoppingQuantityResponseDTO;
import danofra.nutri_balbe.repositories.GroceryShoppingQuantityDAO;
import danofra.nutri_balbe.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroceryShoppingQuantityService {
    @Autowired
    private GroceryShoppingQuantityDAO groceryShoppingQuantityDAO;

    @Autowired
    private GroceryShoppingService groceryShoppingService;

    @Autowired
    private ProductDAO productDAO;

    public GroceryShoppingQuantityResponseDTO save(int userId, int quantity, String productName) {
        GroceryShopping groceryShopping = groceryShoppingService.findByUserId(userId);
        Product product = productDAO.findById(productName).orElseThrow(() -> new BadRequestException("Product whit id " + productName + " does not exist!"));
        Optional<GroceryShoppingQuantity> existingGroceryShoppingQuantity = groceryShoppingQuantityDAO.findByGroceryShoppingIdAndProductName(groceryShopping.getId(), productName);
        GroceryShoppingQuantity groceryShoppingQuantity;
        if (existingGroceryShoppingQuantity.isPresent()) {
            groceryShoppingQuantity = existingGroceryShoppingQuantity.get();
            groceryShoppingQuantity.setQuantity(groceryShoppingQuantity.getQuantity() + quantity);
        } else {
            groceryShoppingQuantity = new GroceryShoppingQuantity();
            groceryShoppingQuantity.setGroceryShopping(groceryShopping);
            groceryShoppingQuantity.setProduct(product);
            groceryShoppingQuantity.setQuantity(quantity);
        }
        groceryShoppingQuantityDAO.save(groceryShoppingQuantity);
        return new GroceryShoppingQuantityResponseDTO(groceryShoppingQuantity.getQuantity(), groceryShoppingQuantity.getProduct().getName());
    }

    public Page<GroceryShoppingQuantity> findByUserId(int userId, int page, int size, String sortBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.groceryShoppingQuantityDAO.findByGroceryShoppingId(groceryShoppingService.findByUserId(userId).getId(), pageable);
    }

    public GroceryShoppingQuantityResponseDTO findByUserIdAndProductNameAndUpdateQuantity(int userId, String productName, int quantity) {
        GroceryShopping groceryShopping = groceryShoppingService.findByUserId(userId);
        Product product = productDAO.findById(productName).orElseThrow(() -> new BadRequestException("Product whit id " + productName + " does not exist!"));
        List<GroceryShoppingQuantity> groceryShoppingQuantity = this.groceryShoppingQuantityDAO.findByGroceryShoppingId(groceryShopping.getId());
        GroceryShoppingQuantityResponseDTO response = null;
        for (GroceryShoppingQuantity groceryShoppingQuantity1 : groceryShoppingQuantity) {
            if ((groceryShoppingQuantity1.getProduct().getName() == productName)) {
                groceryShoppingQuantity1.setQuantity(quantity);
                groceryShoppingQuantityDAO.save(groceryShoppingQuantity1);
                response = new GroceryShoppingQuantityResponseDTO(groceryShoppingQuantity1.getQuantity(), groceryShoppingQuantity1.getProduct().getName());
            }
        }
        return response;
    }

    public void findByUserIdAndProductNameAndDelete(int userId, String productName) {
        GroceryShopping groceryShopping = groceryShoppingService.findByUserId(userId);
        Product product = productDAO.findById(productName).orElseThrow(() -> new BadRequestException("Product whit id " + productName + " does not exist!"));
        List<GroceryShoppingQuantity> groceryShoppingQuantity = this.groceryShoppingQuantityDAO.findByGroceryShoppingId(groceryShopping.getId());
        for (GroceryShoppingQuantity groceryShoppingQuantity1 : groceryShoppingQuantity) {
            if (Objects.equals(groceryShoppingQuantity1.getProduct().getName(), productName)) {
                groceryShoppingQuantityDAO.delete(groceryShoppingQuantity1);
            }
        }
    }


}
