package org.peacockteam.model;

import com.google.gson.annotations.Expose;

public class Products {
    @Expose
    public String text;

    @Override
    public String toString() {
        return text;
    }
}
