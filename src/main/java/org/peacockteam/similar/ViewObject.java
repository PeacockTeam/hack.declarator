package org.peacockteam.similar;



import java.util.ArrayList;
import java.util.List;

public class ViewObject {

    public final String name;

    public String[] converted;

    public int convertedSize;

    public ViewObject(String name){
        this.name = name;

        this.convert();
    }

    protected void convert(){
        String name = this.name.toLowerCase();

        name = Utils.cyr2lat(name);
        name = name.replaceAll("[^a-z ]", " ");

        this.converted =  name.split(" ");

        List<String> tmp = new ArrayList<String>();

        for (String aConverted : this.converted) {
            if (aConverted.length() > 2) {
                tmp.add(aConverted);
            }
        }

        String[] stockArr = new String[tmp.size()];
        stockArr = tmp.toArray(stockArr);

        this.converted = stockArr;
        this.convertedSize = this.converted.length;
    }




}
