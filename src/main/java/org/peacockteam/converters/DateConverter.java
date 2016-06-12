package org.peacockteam.converters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements JsonDeserializer<Date> {
    private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static DateFormat df4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String stringDate = jsonElement.getAsString();
        Date date = null;
        try {
            if (stringDate.length() >= 29 ) {
                date = df1.parse(stringDate);
            } else if (stringDate.length() >= 20) {
                date = df2.parse(stringDate);
            } else if (stringDate.length() >= 19) {
                date = df4.parse(stringDate);
            } else if (stringDate.length() >= 10) {
                date = df3.parse(stringDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
