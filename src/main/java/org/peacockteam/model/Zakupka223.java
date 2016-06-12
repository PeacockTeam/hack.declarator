package org.peacockteam.model;

import java.util.Date;

public class Zakupka223 extends Zakupka {
    private final static String FZ_223 = "223";
    private Date createDateTime;

    protected Zakupka223() {
        super(FZ_223);
    }

    public long getDate() {
        return createDateTime != null? createDateTime.getTime(): 0;
    }
}
