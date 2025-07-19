package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sda.dasgarage.utils.LeasingPdfGenerator;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/admin")
public class LeasingController {

    @Autowired
    private LeasingPdfGenerator pdfGenerator;

    // Afisare pagina HTML leasing-admin
    @GetMapping("/leasing-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String showLeasingAdminPage() {
        return "leasing-admin";
    }

    // Generare PDF leasing
    @GetMapping("/generare-pdf-leasing")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> genereazaPdfLeasing(
            @RequestParam("client") String client,
            @RequestParam("bun") String bunFinantat,
            @RequestParam("valoare") double valoare,
            @RequestParam("perioada") int perioada,
            @RequestParam("dobanda") double dobanda,
            @RequestParam("reziduala") double rezidualaProc
    ) {
        ByteArrayInputStream pdf = pdfGenerator.generateLeasingPdf(client, bunFinantat, valoare, perioada, rezidualaProc, dobanda);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=oferta_leasing.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}



