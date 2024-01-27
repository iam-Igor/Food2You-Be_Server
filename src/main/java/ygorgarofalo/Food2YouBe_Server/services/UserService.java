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
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.repositories.UserRepo;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Cloudinary cloudinary;


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
}
