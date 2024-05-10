package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.FoodStorage;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.FoodStorageResponseDTO;
import danofra.nutri_balbe.repositories.FoodStorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodStorageService {
    @Autowired
    private FoodStorageDAO foodStorageDAO;

    public FoodStorageResponseDTO save(User userId) {
        FoodStorage foodStorage = new FoodStorage(userId);
        if (foodStorageDAO.findByUserId(userId.getId()).isEmpty()) {
            foodStorageDAO.save(foodStorage);
        }
        return new FoodStorageResponseDTO(userId.getName(), userId.getSurname(), foodStorage.getId());
    }

    public FoodStorage findByUserId(int userId) {
        return foodStorageDAO.findByUserId(userId).orElseThrow(() -> new BadRequestException("FoodStorage whit user " + userId + " does not exist!"));
    }
}
