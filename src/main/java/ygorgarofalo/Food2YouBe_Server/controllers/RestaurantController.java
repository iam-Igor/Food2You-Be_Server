package ygorgarofalo.Food2YouBe_Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.services.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;


    @GetMapping // test ok
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getRestaurants();
    }


    @GetMapping("/{id}") // test ok
    public Restaurant findById(@PathVariable long id) {
        return restaurantService.findById(id);
    }


    @GetMapping("/city/{city}")// test ok
    public List<Restaurant> getRestaurantasByCity(@PathVariable String city) {
        return restaurantService.getRestaurantsByCityName(city);
    }


}
