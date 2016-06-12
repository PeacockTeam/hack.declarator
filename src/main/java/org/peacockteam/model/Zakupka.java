package org.peacockteam.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public abstract class Zakupka {
    private final String fz;
    private double price;
    private String id;
    private String printFormUrl = "";
    private String contractUrl;
    private Company customer;
    private List<Company> suppliers;
    @Expose
    private Products products;

    protected Zakupka(String fz) {
        this.fz = fz;
    }


    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return contractUrl != null? contractUrl: printFormUrl;
    }

    public abstract long getDate();

    public String getFz() {
        return fz;
    }

    public Company getCustomer() {
        return customer;
    }

    public List<Company> getSuppliers() {
        return suppliers;
    }

    public String getProducts() {
        return products != null? products.text: "";
    }
}
