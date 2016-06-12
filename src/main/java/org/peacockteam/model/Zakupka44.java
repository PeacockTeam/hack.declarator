package org.peacockteam.model;

import java.util.Date;

public class Zakupka44 extends Zakupka {
    private final static String FZ_44 = "44";
    private Date signDate;

    protected Zakupka44() {
        super(FZ_44);
    }

    public long getDate() {
        return signDate!= null?signDate.getTime():0;
    }
}
