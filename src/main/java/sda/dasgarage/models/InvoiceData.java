package sda.dasgarage.models;

public class InvoiceData {
    private String customerName;
    private String cui;
    private String regCom;
    private String country;
    private String county;
    private String street;
    private String iban;
    private String bank;
    private String email;
    private String vin; // seria de caroserie (optional)

    public InvoiceData() {}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getClientName() {
        return customerName;
    }

    public void setClientName(String clientName) {
        this.customerName = clientName;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getRegCom() {
        return regCom;
    }

    public void setRegCom(String regCom) {
        this.regCom = regCom;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}