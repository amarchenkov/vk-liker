package com.github.vk.bot.account.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created at 30.09.2017 16:40
 *
 * @author Andrey
 */
public class TestUtils {
    public static String getResourceAsString(String filename) throws IOException {
        InputStream is = TestUtils.class.getClassLoader().getResourceAsStream(filename);
        return IOUtils.toString(is, Charset.forName("UTF-8"));
    }
}
