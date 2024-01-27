package ygorgarofalo.Food2YouBe_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.repositories.ProductRepo;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;


    public Page<Product> getProducts(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return productRepo.findAll(pageable);
    }


    // torna tutti i prodotti disponibili in base a id del ristorante
    public List<Product> getProductsByRestaurantId(long rest_id) {
        return productRepo.findByRestaurantId(rest_id);
    }

    public Product findById(long id) {
        return productRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
