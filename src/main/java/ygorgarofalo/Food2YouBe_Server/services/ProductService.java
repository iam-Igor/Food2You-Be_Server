package ygorgarofalo.Food2YouBe_Server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.repositories.ProductRepo;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    private Cloudinary cloudinary;


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


    // patch con multi part file e id del prodotto tramite path variable
    public String uploadProductImage(MultipartFile file, long product_id) throws IOException {
        Product found = this.findById(product_id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        found.setImageUrl(url);
        productRepo.save(found);
        return url;
    }

    public void findByIdAndDelete(long id) {
        Product found = this.findById(id);
        productRepo.delete(found);
    }
}
