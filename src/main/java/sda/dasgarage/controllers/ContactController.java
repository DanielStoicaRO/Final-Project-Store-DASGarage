package sda.dasgarage.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sda.dasgarage.service.EmailService;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contact")
    public String handleContactForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("message") String message
    ) {
        String subject = "Mesaj nou de pe site de la " + name;
        String content = "Nume: " + name + "\n"
                + "Email: " + email + "\n"
                + "Telefon: " + phone + "\n\n"
                + "Mesaj:\n" + message;

        try {
            emailService.sendSimpleMessage("Stoica.Daniel@icloud.com", subject, content);
        } catch (Exception e) {
            return "redirect:/contact?error";
        }

        return "redirect:/contact?success";
    }
}