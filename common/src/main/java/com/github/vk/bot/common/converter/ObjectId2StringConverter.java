package com.github.vk.bot.common.converter;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

/**
 * Created at 26.09.2017 17:16
 *
 * @author AMarchenkov
 */
public class ObjectId2StringConverter implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {

    @Override
    public JsonElement serialize(ObjectId id, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(id.toString());
    }

    @Override
    public ObjectId deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new ObjectId(jsonElement.getAsString());
    }
}
