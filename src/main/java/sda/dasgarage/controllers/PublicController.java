package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sda.dasgarage.entities.Car;
import sda.dasgarage.repositories.CarRepository;

import java.util.List;

@Controller
public class PublicController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/allcars")
    public String viewAllCars(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "allcars";
    }

    @GetMapping("/car/{id}")
    public String viewCarDetails(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Masina nu a fost gasita"));
        model.addAttribute("car", car);
        return "car-details";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/cum-cumpar")
    public String cumCumpar() {
        return "cum-cumpar";
    }

    @Controller
    public class FrontpageController {

        @GetMapping("/testimoniale")
        public String showTestimonials() {
            return "testimoniale";
        }

        // alte rute existente...
    }

    @GetMapping("/leasing")
    public String leasingPage() {
        return "leasing";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }



}