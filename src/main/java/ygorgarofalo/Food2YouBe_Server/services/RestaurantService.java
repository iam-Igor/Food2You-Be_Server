package ygorgarofalo.Food2YouBe_Server.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductListPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.RestaurantpayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.RestaurantRepo;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    ProductService productService;


    public Page<Restaurant> getRestaurants(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return restaurantRepo.findAll(pageable);
    }


    public Restaurant findById(long id) {
        return restaurantRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    // questo lo userò per cercare ristornati divisi per città
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
}