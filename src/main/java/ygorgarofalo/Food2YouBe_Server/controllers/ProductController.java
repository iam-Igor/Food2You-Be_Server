package ygorgarofalo.Food2YouBe_Server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductService productService;


    @GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String order) {
        return productService.getProducts(page, size, order);
    }


    @GetMapping("/{id}")
    public List<Product> getProductsByRestaurant(@PathVariable long id) {
        return productService.getProductsByRestaurantId(id);
    }


    @GetMapping("/search")
    public Page<Product> findByName(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String order,
                                    @RequestParam String name) {
        return productService.findProductsByName(page, size, order, name);
    }
}
