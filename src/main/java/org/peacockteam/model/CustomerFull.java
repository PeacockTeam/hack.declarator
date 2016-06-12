package org.peacockteam.model;

public class CustomerFull extends CompanyFull {
    private String shortName;
    private String fullName = "";

    public String getName() {
        return shortName != null? shortName: fullName;
    }
}
