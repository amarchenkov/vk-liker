package com.github.vk.bot.common.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bson.types.ObjectId;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created at 11.10.2017 12:34
 *
 * @author AMarchenkov
 */
public class ObjectId2StringConverterTest {

    private static final String TEST_OBJECT_ID_STRING = "59d627c3f557c64acd8491dc";
    private static final ObjectId TEST_OBJECT_ID = new ObjectId(TEST_OBJECT_ID_STRING);

    @Test
    public void shouldReturnStringRepresentationOfObjectId() {
        ObjectId2StringConverter converter = new ObjectId2StringConverter();
        JsonElement serialize = converter.serialize(TEST_OBJECT_ID, null, null);
        assertThat(serialize.getAsString(), is(equalTo(TEST_OBJECT_ID_STRING)));
    }

    @Test
    public void shouldReturnObjectIdFromString() {
        ObjectId2StringConverter converter = new ObjectId2StringConverter();
        JsonElement jsonElement = new JsonPrimitive(TEST_OBJECT_ID_STRING);
        ObjectId deserialize = converter.deserialize(jsonElement, null, null);
        assertThat(deserialize, is(equalTo(TEST_OBJECT_ID)));
    }
}
