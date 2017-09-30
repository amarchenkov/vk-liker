package com.github.vk.bot.account;

import com.github.vk.bot.account.endpoint.converter.ObjectId2StringConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountServiceApplication extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/account").allowedOrigins("http://localhost:8080");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(ObjectId.class, new ObjectId2StringConverter())
                .create();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        converters.add(converter);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
