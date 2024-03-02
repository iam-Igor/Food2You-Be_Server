package ygorgarofalo.Food2YouBe_Server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Order;
import ygorgarofalo.Food2YouBe_Server.entities.OrderStatus;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.BadRequestException;
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


    @Autowired
    private UserService userService;

    // Accessibile solo a admin
    public Page<Order> getOrders(int page, int size, String orderBy) {
        if (size >= 100) size = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        Specification<Order> spec = (root, query, cb) -> cb.isNotNull(root.get("user"));

        return orderRepo.findAll(spec, pageable);
    }


    // metodo che piazza una ordinazione, nel payload arriva un array di id di prodotti
    //e un id di ristorante, lo user lo passerò attraverso auth principal

    public Order placeOrder(OrderPayloadDTO payload, User user) {
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
        if (payload.isPromoCodePresent()) {
            double discount = total * 0.2;
            newOrder.setTotalAmount(total - discount);
//            newOrder.setPromoCodeUsed(true);
        } else {
            newOrder.setTotalAmount(total);
//            newOrder.setPromoCodeUsed(false);
        }
        newOrder.setPaymentAccepted(true);
        newOrder.setUserPosition(payload.userAddress());
        return orderRepo.save(newOrder);
    }


    public Order findById(long id) {
        return orderRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public void findByIdAndDelete(long id) {
        Order found = this.findById(id);
        orderRepo.delete(found);
    }


    // richiamato automaticamente quando il rider arriva a destinazione
    // PATCH
    public void setOrderDelivered(long id, User user) {

        Order found = this.findById(id);

        if (user.getOrderList().stream().anyMatch(order -> order.getId() == found.getId())) {

            found.setOrderStatus(OrderStatus.CONSEGNATO);
            orderRepo.save(found);
        } else {

            throw new BadRequestException("Ordine non corrispondente all'utente loggato!");
        }
    }


    // metodo che stampa un ordine (scontrino)
    public Order printOrder(long order_id, User user) {
        User found = userService.findById(user.getId());
        return orderRepo.getOrderByuserIdandOrderId(order_id, found.getId());
    }


}
