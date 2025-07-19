package sda.dasgarage.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private String color;
    private int year;
    private int km;
    private String engine;

    @Column(name = "price_net")
    private double priceNet;

    @Column(name = "price_gross")
    private double priceGross;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "car_image_paths", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "image_paths")
    private List<String> imagePaths;

    private boolean tvaDeductibil;
    private boolean generatePdf;

    @Column(nullable = false)
    private boolean showOnSite = false;

    // Getteri si setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public double getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(double priceNet) {
        this.priceNet = priceNet;
    }

    public double getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(double priceGross) {
        this.priceGross = priceGross;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public boolean isTvaDeductibil() {
        return tvaDeductibil;
    }

    public void setTvaDeductibil(boolean tvaDeductibil) {
        this.tvaDeductibil = tvaDeductibil;
    }

    public boolean isGeneratePdf() {
        return generatePdf;
    }

    public void setGeneratePdf(boolean generatePdf) {
        this.generatePdf = generatePdf;
    }

    public boolean isShowOnSite() {
        return showOnSite;
    }

    public void setShowOnSite(boolean showOnSite) {
        this.showOnSite = showOnSite;
    }
}