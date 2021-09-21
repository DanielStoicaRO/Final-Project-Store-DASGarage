package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sda.dasgarage.entities.ProductEntity;
import sda.dasgarage.repositories.ProductRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    public ProductController() {
        System.out.println(getClass().getSimpleName() + " created");
    }

    @GetMapping("/frontpage")
    public ModelAndView getFrontPage(String keyword) {
        ModelAndView modelAndView = new ModelAndView("frontpage");
        //        search
        if (keyword!= null){
            modelAndView.addObject("stockList", productRepository.findByKeyword(keyword));
        } else {
            modelAndView.addObject("stockList", productRepository.findAll());
            return modelAndView;
        }
        return modelAndView;
    }

    @GetMapping("/product/add")
    public ModelAndView productAdd() {
        ModelAndView modelAndView = new ModelAndView("productEdit");
        modelAndView.addObject("product", new ProductEntity());
        return modelAndView;
    }

    @PostMapping(value="/product/save", consumes = {"multipart/form-data"})
    public ModelAndView productSave(@ModelAttribute("product") ProductEntity productEntity, @RequestParam("file") MultipartFile file) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");
        String path1 = "target/classes/static/imagines";
        String path2 = "src/main/resources/static/imagines";
        String filename = file.getOriginalFilename();
        saveFile(path1, filename, file);
        saveFile(path2, filename, file);
        productEntity.setImagineUrl("/imagines/" + filename);
        productRepository.save(productEntity);
        return modelAndView;
    }

    public void saveFile(String path, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(path);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
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
