package ygorgarofalo.Food2YouBe_Server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Order;
import ygorgarofalo.Food2YouBe_Server.entities.OrderStatus;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.OrderPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.OrderRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantService restaurantService;


    // Accessibile solo a admin
    public Page<Order> getOrders(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return orderRepo.findAll(pageable);
    }


    // metodo che piazza una ordinazione, nel payload arriva un array di id di prodotti
    //e un id di ristornate, lo user lo passerò attraverso auth principal

    public Order PlaceOrder(OrderPayloadDTO payload, User user) {
        List<Product> productList = new ArrayList<>();
        Order newOrder = new Order();
        double total = 0.0;
        for (Long productId : payload.productIds()) {
            Product foundProduct = productService.findById(productId);
            productList.add(foundProduct);
            total += foundProduct.getPrice();
        }
        newOrder.setOrderTime(LocalDate.now());
        newOrder.setOrderStatus(OrderStatus.IN_PREPARAZIONE);
        newOrder.setUser(user);
        newOrder.setProductList(productList);
        newOrder.setRestaurant(restaurantService.findById(payload.restaurantId()));
        newOrder.setTotalAmount(total);
        newOrder.setPaymentAccepted(true);
        return orderRepo.save(newOrder);
    }


    public Order findById(long id) {
        return orderRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public void findByIdAndDelete(long id) {
        Order found = this.findById(id);
        orderRepo.delete(found);
    }

}
