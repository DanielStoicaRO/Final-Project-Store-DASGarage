package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import sda.dasgarage.entities.CartEntity;
import sda.dasgarage.entities.UserEntity;
import sda.dasgarage.repositories.CartRepository;
import sda.dasgarage.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public CartController() {
        System.out.println(this.getClass().getSimpleName() + " created");
    }

    public Optional<User> getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @GetMapping("/get-cart")
    public ModelAndView getCard() {
        ModelAndView modelAndView = new ModelAndView("cart");
        Optional<User> user = getLoggedInUser();
        if (user.isPresent()) {
            modelAndView.addObject("cart", cartRepository.findAllByUser_Username(user.get().getUsername()));
        } else {
            modelAndView.addObject("cart", new ArrayList<>());
        }
        return modelAndView;
    }

    //    Homework
    @GetMapping("/delete-cart/{id}")
    public ModelAndView deleteCard(@PathVariable Integer id) {
        Optional<CartEntity> cartEntity = cartRepository.findById(id);
        if (cartEntity.isPresent()) {
            cartRepository.delete(cartEntity.get());
            ModelAndView modelAndView = new ModelAndView("redirect:/get-cart");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/errorHtml");
        return modelAndView;
    }

    //    Homework
    @GetMapping("/add-cart/{id}")
    public ModelAndView addCard(@PathVariable Integer id, CartEntity cartEntity) {
        ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");

        Optional<User> user = getLoggedInUser();
        if (user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            cartEntity.setUserId(userEntity.getUserId());
            cartEntity.setProductId(id);
            cartEntity.setQuantity(1);
            cartRepository.save(cartEntity);
        } else {
            modelAndView.addObject("cart", new ArrayList<>());
        }
        return modelAndView;
    }


}
