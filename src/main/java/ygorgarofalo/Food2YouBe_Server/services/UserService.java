package ygorgarofalo.Food2YouBe_Server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.CreditCard;
import ygorgarofalo.Food2YouBe_Server.entities.Order;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.UserPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.OrderRepo;
import ygorgarofalo.Food2YouBe_Server.repositories.UserRepo;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PasswordEncoder bcrypt;

    // Accessibile solo a admin
    public Page<User> getUsers(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepo.findAll(pageable);
    }


    // Accessibile solo a admin
    public User findById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    // Accessibile solo a admin
    public void findByIdAndDelete(long id) {
        User found = this.findById(id);

        for (Order order : found.getOrderList()) {
            order.setUser(null);

        }
        orderRepo.saveAll(found.getOrderList());
        userRepo.delete(found);
    }


    // Accessibile solo a admin
    public User findByEmail(String email) throws NotFoundException {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }


    // patch
    public String uploadAvatarImage(MultipartFile file, User user) throws IOException {

        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        user.setAvatarUrl(url);
        userRepo.save(user);
        return url;
    }


    // post per modificare i dati di un utente che arriva tramite auth principal

    public User updateUser(User user, UserPayloadDTO payload) {
        if (user != null) {
            user.setAddress(payload.address());
            user.setName(payload.name());
            user.setLastname(payload.lastname());
            user.setPassword(bcrypt.encode(payload.password()));
            user.setEmail(payload.email());
            user.setUsername(payload.username());
            return userRepo.save(user);
        } else {
            return user;
        }
    }


    public List<Order> getOrdersList(User user) {
        return userRepo.getAllUserOrders(user);
    }


    public CreditCard getCreditCard(User user) {
        User found = this.findById(user.getId());
        return found.getCreditCard();
    }
}
