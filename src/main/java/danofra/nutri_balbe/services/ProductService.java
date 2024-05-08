package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.Product;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.ProductDTO;
import danofra.nutri_balbe.payloads.ProductResponseDTO;
import danofra.nutri_balbe.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public ProductResponseDTO save(ProductDTO newProduct) throws BadRequestException {
        Optional<Product> existingProduct = this.productDAO.findByName(newProduct.name());
        if (existingProduct.isPresent()) {
            throw new BadRequestException("Product " + newProduct.name() + " already exists!");
        }
        Product product = new Product();
        product.setName(newProduct.name());
        product.setCategory(newProduct.category());
        product.setDescription(newProduct.description());
        product.setKcal(newProduct.kcal());
        product.setImage(newProduct.image());
        productDAO.save(product);
        return new ProductResponseDTO(product.getName());
    }

    public Page<Product> getProduct(int page, int size, String sortBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.productDAO.findAll(pageable);
    }

    public Product findByName(String name) {
        return this.productDAO.findByName(name).orElseThrow(() -> new BadRequestException("Product " + name + " does not exist!"));
    }

    public void findByNameAndDelete(String name) {
        Product product = this.findByName(name);
        this.productDAO.delete(product);
    }
}
