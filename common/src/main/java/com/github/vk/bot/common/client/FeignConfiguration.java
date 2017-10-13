package com.github.vk.bot.common.client;

import com.github.vk.bot.common.converter.LocalDateTimeConverter;
import com.github.vk.bot.common.converter.ObjectId2StringConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.time.LocalDateTime;

/**
 * Created at 06.10.2017 14:43
 *
 * @author AMarchenkov
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public Decoder feignDecoder() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(ObjectId.class, new ObjectId2StringConverter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                .create();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public Encoder feignEncoder() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(ObjectId.class, new ObjectId2StringConverter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                .create();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converter);
        return new SpringEncoder(objectFactory);
    }
}
