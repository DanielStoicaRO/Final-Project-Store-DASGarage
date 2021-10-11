package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import sda.dasgarage.repositories.CartRepository;
import sda.dasgarage.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class GeneralController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    //    cartCount/userIsPresent
    public Optional<User> getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @GetMapping("/contactUs")
    public ModelAndView getContactUs() {
        ModelAndView modelAndView = new ModelAndView("contactUs");
        Optional<User> user = getLoggedInUser();
        if (user.isPresent()) {
//            cart count
            Integer userId = userRepository.findUserEntityByUsername(user.get().getUsername()).getUserId();
            Long cartLenght = cartRepository.countAllByUserId(userId);
            modelAndView.addObject("cartSize", cartLenght);
        }
        return modelAndView;
    }

    @GetMapping("/leasing")
    public ModelAndView getLeasing() {
        ModelAndView modelAndView = new ModelAndView("leasing");
        Optional<User> user = getLoggedInUser();
        if (user.isPresent()) {
//            cart count
            Integer userId = userRepository.findUserEntityByUsername(user.get().getUsername()).getUserId();
            Long cartLenght = cartRepository.countAllByUserId(userId);
            modelAndView.addObject("cartSize", cartLenght);
        }
        return modelAndView;
    }

    @GetMapping("/pay")
    public ModelAndView getPay() {
        ModelAndView modelAndView = new ModelAndView("pay");
        return modelAndView;
    }


}
