package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sda.dasgarage.entities.ProductEntity;
import sda.dasgarage.repositories.ProductRepository;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    public ProductController() {
        System.out.println(getClass().getSimpleName() + " created");
    }

    @GetMapping("/frontpage")
    public ModelAndView getFrontPage() {
        ModelAndView modelAndView = new ModelAndView("frontpage");
        modelAndView.addObject("stockList", productRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/product/add")
    public ModelAndView productAdd() {
        ModelAndView modelAndView = new ModelAndView("productEdit");
        modelAndView.addObject("product", new ProductEntity());
        return modelAndView;
    }

    @PostMapping("/product/save")
    public ModelAndView productSave(@ModelAttribute("product") ProductEntity productEntity) {
        ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");
        productRepository.save(productEntity);
        return modelAndView;
    }

    @GetMapping("/product/view/{id}")
    public ModelAndView productView(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("productView");
        modelAndView.addObject("product", productRepository.getById(id));
        return modelAndView;
    }

    @GetMapping("/product/edit/{id}")
    public ModelAndView productEdit(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("productEdit");
        modelAndView.addObject("product", productRepository.getById(id));
        return modelAndView;
    }

    @GetMapping("/product/delete/{id}")
    public ModelAndView productDelete(@PathVariable Integer id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isPresent()) {
            productRepository.delete(productEntity.get());
            ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("errorHtml");
        return modelAndView;
    }

}
