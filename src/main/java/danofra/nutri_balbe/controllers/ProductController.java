package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.Product;
import danofra.nutri_balbe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public Page<Product> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return this.productService.getProduct(page, size, sortBy);
    }

    @GetMapping("/{productName}")
    public Product findByName(@PathVariable String productName) {
        return this.productService.findByName(productName);
    }

    @DeleteMapping("/{productName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByNameAndDelete(@PathVariable String productName) {
        productService.findByNameAndDelete(productName);
    }
}
