package org.peacockteam.model;

import java.util.Date;

public class Zakupka94 extends Zakupka {
    private final static String FZ_94 = "94";
    private Date signDate;

    protected Zakupka94() {
        super(FZ_94);
    }

    public long getDate() {
        return signDate!= null?signDate.getTime():0;
    }
}
