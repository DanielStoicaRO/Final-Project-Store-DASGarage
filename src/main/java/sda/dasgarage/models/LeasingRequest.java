package sda.dasgarage.models;

public class LeasingRequest {

    private String client;
    private String bunFinantat;
    private double pretFaraTVA;
    private int perioada;
    private double avansProc;
    private double dobanda;
    private double rezidualaProc;

    // Getteri si setteri
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBunFinantat() {
        return bunFinantat;
    }

    public void setBunFinantat(String bunFinantat) {
        this.bunFinantat = bunFinantat;
    }

    public double getPretFaraTVA() {
        return pretFaraTVA;
    }

    public void setPretFaraTVA(double pretFaraTVA) {
        this.pretFaraTVA = pretFaraTVA;
    }

    public int getPerioada() {
        return perioada;
    }

    public void setPerioada(int perioada) {
        this.perioada = perioada;
    }

    public double getAvansProc() {
        return avansProc;
    }

    public void setAvansProc(double avansProc) {
        this.avansProc = avansProc;
    }

    public double getDobanda() {
        return dobanda;
    }

    public void setDobanda(double dobanda) {
        this.dobanda = dobanda;
    }

    public double getRezidualaProc() {
        return rezidualaProc;
    }

    public void setRezidualaProc(double rezidualaProc) {
        this.rezidualaProc = rezidualaProc;
    }
}