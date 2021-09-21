package sda.dasgarage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/contactUs")
    public String getAboutUs(){
        return "contactUs";
    }


    @GetMapping("/leasing")
    public String getLeasing(){return "leasing";}

    @GetMapping("/testPage")
    public String getTesting(){return "testPage";}

    @GetMapping("/cart")
    public String getCart(){return "cart";}

}
