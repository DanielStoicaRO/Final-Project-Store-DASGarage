
package sda.dasgarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sda.dasgarage.entities.Car;
import sda.dasgarage.models.InvoiceData;
import sda.dasgarage.repositories.CarRepository;
import sda.dasgarage.utils.PdfGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    @GetMapping("/add-car")
    public String showAddCarForm(Model model, HttpServletRequest request) {
        model.addAttribute("car", new Car());
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);
        return "addcar";
    }

    @GetMapping("/frontpage")
    public String frontpage(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "frontpage";
    }

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car,
                         @RequestParam(value = "images", required = false) MultipartFile[] images,
                         @RequestParam(value = "tvaDeductibil", required = false) String tvaDeductibil) {
        try {
            List<String> imagePaths = new ArrayList<>();
            if (images != null) {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                for (MultipartFile image : images) {
                    if (!image.isEmpty()) {
                        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                        File destFile = new File(UPLOAD_DIR + fileName);
                        image.transferTo(destFile);
                        imagePaths.add(fileName);
                    }
                }
            }

            car.setImagePaths(imagePaths);
            car.setTvaDeductibil(tvaDeductibil != null);

            carRepository.save(car);

            return "redirect:/admin/confirmation";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditCarForm(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Masina nu a fost gasita"));
        model.addAttribute("car", car);
        return "editcar";
    }

    @PostMapping("/edit/{id}")
    public String editCar(@PathVariable Long id,
                          @ModelAttribute Car updatedCar,
                          @RequestParam(value = "tvaDeductibil", required = false) String tvaDeductibil) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Masina nu a fost gasita"));

        car.setMake(updatedCar.getMake());
        car.setModel(updatedCar.getModel());
        car.setYear(updatedCar.getYear());
        car.setKm(updatedCar.getKm());
        car.setEngine(updatedCar.getEngine());
        car.setColor(updatedCar.getColor());
        car.setPriceNet(updatedCar.getPriceNet());
        car.setPriceGross(updatedCar.getPriceGross());
        car.setTvaDeductibil(tvaDeductibil != null);
        car.setDescription(updatedCar.getDescription());

        carRepository.save(car);

        return "redirect:/admin/stock";
    }

    @GetMapping("/stock")
    public String viewStock(Model model, HttpServletRequest request) {
        model.addAttribute("cars", carRepository.findAll());
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);
        return "stock";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carRepository.findById(id).ifPresent(car -> {
            if (car.getImagePaths() != null) {
                for (String fileName : car.getImagePaths()) {
                    File imageFile = new File(UPLOAD_DIR + fileName);
                    if (imageFile.exists()) imageFile.delete();
                }
            }

            String pdfPath = "src/main/resources/static/pdf/oferta_" + car.getId() + ".pdf";
            File pdfFile = new File(pdfPath);
            if (pdfFile.exists()) pdfFile.delete();

            carRepository.deleteById(id);
        });

        return "redirect:/admin/stock";
    }

    @PostMapping("/generate-pdf/{id}")
    public void generatePdf(@PathVariable Long id, HttpServletResponse response) {
        try {
            Car car = carRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Masina nu a fost gasita"));
            File pdfFile = pdfGenerator.generateCarOfferPdf(car);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + pdfFile.getName());
            response.setContentLength((int) pdfFile.length());

            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pdfFile));
                 BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }

        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea PDF-ului.");
        }
    }

    @GetMapping("/generate-invoice-form/{carId}")
    public String showInvoiceForm(@PathVariable Long carId, Model model, HttpServletRequest request) {
        Optional<Car> car = carRepository.findById(carId);
        if (!car.isPresent()) {
            return "error";
        }
        model.addAttribute("car", car.get());
        model.addAttribute("invoiceData", new InvoiceData());
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);
        model.addAttribute("carId", carId);
        return "invoiceform";
    }

    @PostMapping("/generate-invoice/{carId}")
    public void generateInvoice(@PathVariable Long carId, @ModelAttribute InvoiceData invoiceData, HttpServletResponse response) {
        try {
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new NoSuchElementException("Masina nu a fost gasita"));
            File pdfFile = pdfGenerator.generateInvoicePdf(car, invoiceData);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + pdfFile.getName());
            response.setContentLength((int) pdfFile.length());

            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pdfFile));
                 BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea facturii PDF.");
        }
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "confirmation";
    }

    @GetMapping("/car/{id}")
    public String viewCarDetails(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Masina nu a fost gasita"));
        model.addAttribute("car", car);
        return "car-details";
    }
}




