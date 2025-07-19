package sda.dasgarage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontpageController {

    @GetMapping("/frontpage")
    public String showFrontpage() {
        return "frontpage";
    }

    @GetMapping("/")
    public String redirectToFrontPage() {
        return "redirect:/frontpage";
    }


}