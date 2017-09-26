package com.github.vk.bot.account.endpoint.converter;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created at 26.09.2017 17:16
 *
 * @author AMarchenkov
 */
@Component
public class ObjectId2StringConverter implements Converter<ObjectId, String> {
    @Override
    public String convert(ObjectId id) {
        return id.toString();
    }
}
