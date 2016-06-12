package org.peacockteam.model;

public abstract class CompanyFull extends Company {
    private String ogrn;

    public String getOgrn() {
        return ogrn;
    }

    public abstract String getName();
}
