package com.github.vk.api.models.json.adapter;

import com.github.vk.api.models.json.Response;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created at 22.08.2016 11:26
 *
 * @author AMarchenkov
 */
public class ResponseAdapter implements JsonDeserializer<Response> {

    @Override
    public Response deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

}
