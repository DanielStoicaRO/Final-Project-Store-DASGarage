package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import sda.dasgarage.repositories.ProductRepository;

@Controller
public class ProductController {

    public ProductController() {
        System.out.println(getClass().getSimpleName() + " created");
    }

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/frontpage")
    public ModelAndView getFrontPage() {
         ModelAndView modelAndView = new ModelAndView("frontpage");
         modelAndView.addObject("stockList", productRepository.findAll());
         return modelAndView;
    }

    @GetMapping("/product/view/{id}")
    public ModelAndView productView(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("productView");
        modelAndView.addObject("product", productRepository.getById(id));
        return modelAndView;
    }



}
