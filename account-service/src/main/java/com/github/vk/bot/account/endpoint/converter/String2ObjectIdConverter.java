package com.github.vk.bot.account.endpoint.converter;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created at 26.09.2017 16:47
 *
 * @author AMarchenkov
 */
@Component
public class String2ObjectIdConverter implements Converter<String, ObjectId> {
    @Override
    public ObjectId convert(String s) {
        return new ObjectId(s);
    }
}
