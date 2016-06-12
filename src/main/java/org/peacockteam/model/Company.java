package org.peacockteam.model;

public class Company {
    private String inn;
    private String kpp;

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getId() {
        return inn + ';' + kpp;
    }
}

