package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import sda.dasgarage.repositories.CartRepository;
import sda.dasgarage.repositories.ProductRepository;
import sda.dasgarage.repositories.UserRepository;

import java.util.Optional;

@Controller
public class GeneralController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/contactUs")
    public String getAboutUs(){
        return "contactUs";
    }

//    @GetMapping("/contactUs")
//    public ModelAndView productView(@PathVariable Integer id) {
//        ModelAndView modelAndView = new ModelAndView("contactUs");
//        modelAndView.addObject("product", productRepository.getById(id));
//
//        Optional<User> user = getLoggedInUser();
//        if (user.isPresent()) {
////            cart count
//            Integer userId = userRepository.findUserEntityByUsername(user.get().getUsername()).getUserId();
//            Long cartLenght = cartRepository.countAllByUserId(userId);
//            modelAndView.addObject("cartSize", cartLenght);
//        }
//        return modelAndView;
//    }

    @GetMapping("/leasing")
    public String getLeasing(){return "leasing";}

    @GetMapping("/testPage")
    public String getTesting(){return "testPage";}

    @GetMapping("/cart")
    public String getCart(){return "cart";}

}
