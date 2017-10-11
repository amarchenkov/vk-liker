package com.github.vk.bot.common.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created at 11.10.2017 11:49
 *
 * @author AMarchenkov
 */
public class LocalDateTimeConverterTest {

    private static final String TEST_DATE = "2017-10-10 23:59:59";
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2017, 10, 10, 23, 59, 59);

    @Test
    public void shouldConvertJsonElementToDate() {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        JsonElement element = new JsonPrimitive(TEST_DATE);
        LocalDateTime deserialize = localDateTimeConverter.deserialize(element, null, null);
        assertThat(deserialize, is(equalTo(LOCAL_DATE_TIME)));
    }

    @Test
    public void shouldConvertDateToJsonElement() {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        JsonElement jsonElement = localDateTimeConverter.serialize(LOCAL_DATE_TIME, null, null);
        assertThat(jsonElement.getAsString(), is(equalTo(TEST_DATE)));
    }
}
