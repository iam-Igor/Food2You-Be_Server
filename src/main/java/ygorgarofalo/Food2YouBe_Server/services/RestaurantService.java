package ygorgarofalo.Food2YouBe_Server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductListPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.RestaurantpayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.RestaurantRepo;

import java.io.IOException;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    ProductService productService;

    @Autowired
    private Cloudinary cloudinary;


    public List<Restaurant> getRestaurants() {
        return restaurantRepo.findAll();
    }


    public Restaurant findById(long id) {
        return restaurantRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    // questo lo userò per cercare ristoranti divisi per città
    public List<Restaurant> getRestaurantsByCityName(String cityName) {
        return restaurantRepo.findByCityIgnoreCase(cityName);
    }


    public Restaurant saveRestaurant(RestaurantpayloadDTO body) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(body.name());
        newRestaurant.setCity(body.city());
        newRestaurant.setLongitude(body.longitude());
        newRestaurant.setLatitude(body.latitude());
        newRestaurant.setStreetAddress(body.streetAddress());
        return restaurantRepo.save(newRestaurant);
    }


    // patch
    @Transactional
    public Restaurant addProducts(ProductListPayloadDTO payload) {
        Restaurant found = this.findById(payload.restaurantId());
        List<Product> productList = found.getProductList();

        for (Long productId : payload.productIds()) {
            Product foundProduct = productService.findById(productId);
            foundProduct.setRestaurant(found);
            productList.add(foundProduct);
        }

        return found;

    }

    public void findByIdAndDelete(long id) {
        Restaurant found = this.findById(id);
        restaurantRepo.delete(found);
    }


    // patch
    public String uploadRestaurantImage(MultipartFile file, long rest_id) throws IOException {
        Restaurant found = this.findById(rest_id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        found.setImageUrl(url);
        restaurantRepo.save(found);
        return url;
    }
}
