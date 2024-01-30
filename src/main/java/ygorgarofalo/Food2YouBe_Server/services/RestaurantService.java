package ygorgarofalo.Food2YouBe_Server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.entities.Summary;
import ygorgarofalo.Food2YouBe_Server.exceptions.BadRequestException;
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
        switch (body.summary()) {
            case "PIZZA":
                newRestaurant.setSummary(Summary.PIZZA);
                break;
            case "FAST_FOOD":
                newRestaurant.setSummary(Summary.FAST_FOOD);
                break;
            case "SUSHI":
                newRestaurant.setSummary(Summary.SUSHI);
                break;
            case "KEBAB":
                newRestaurant.setSummary(Summary.KEBAB);
                break;
            case "PASTA":
                newRestaurant.setSummary(Summary.PASTA);
                break;
            default:
                throw new BadRequestException("Errore nella sintassi del campo summary.");
        }
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


    public List<Restaurant> getRestaurantsByCityAndSummary(String city, String summary) {
        return restaurantRepo.selectByCityAndSummary(city, summary);
    }


    public List<Product> getRestaurantProductList(long id) {

        Restaurant found = this.findById(id);

        return found.getProductList();
    }
}
