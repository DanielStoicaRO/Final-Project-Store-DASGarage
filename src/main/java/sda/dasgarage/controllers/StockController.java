//package sda.dasgarage.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import sda.dasgarage.entities.Car;
//import sda.dasgarage.repositories.CarRepository;
//import sda.dasgarage.utils.PdfGenerator;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.NoSuchElementException;
//
//@Controller
//@RequestMapping("/admin")
//public class StockController {
//
//    private final CarRepository carRepository;
//    private final PdfGenerator pdfGenerator;
//
//    public StockController(CarRepository carRepository, PdfGenerator pdfGenerator) {
//        this.carRepository = carRepository;
//        this.pdfGenerator = pdfGenerator;
//    }
//
//    @PostMapping("/generate-pdf/{id}")
//    public void generatePdf(@PathVariable Long id, HttpServletResponse response) {
//        try {
//            Car car = carRepository.findById(id)
//                    .orElseThrow(() -> new NoSuchElementException("Masina nu a fost gasita"));
//            File pdfFile = pdfGenerator.generateCarOfferPdf(car);
//
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "inline; filename=" + pdfFile.getName());
//            response.setContentLength((int) pdfFile.length());
//
//            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pdfFile));
//                 BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
//
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//                outputStream.flush();
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Eroare la generarea PDF-ului.");
//        }
//    }
//}
