package org.peacockteam.converters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.peacockteam.model.Products;

import java.lang.reflect.Type;

public class ProductsConverter implements JsonDeserializer<Products> {
    public Products deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Products products = new Products();
        products.text = jsonElement.getAsJsonArray().toString();
        return products;
    }
}
