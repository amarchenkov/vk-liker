package com.github.vk.liker;

import com.github.vk.api.enums.Display;
import com.github.vk.api.enums.ResponseType;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.liker.exception.DriverNotFoundException;
import com.github.vk.liker.source.Source;
import com.github.vk.liker.source.impl.FileSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * Created at 17.08.2016 10:51
 *
 * @author AMarchenkov
 */
@EnableMBeanExport
@SpringBootApplication
@EnableConfigurationProperties
public class Application implements CommandLineRunner {

    private static final Logger LOG = LogManager.getLogger(Application.class);

    private Source fileSource;

    @Autowired
    @Qualifier("fileSource")
    public void setFileSource(Source fileSource) {
        this.fileSource = fileSource;
    }

    /**
     * Main method
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);//NOSONAR
    }

    /**
     * Run method
     *
     * @param args command line args
     * @throws Exception generic exception
     */
    @Override
    public void run(String... args) throws Exception {
        if (System.getProperty("webdriver.gecko.driver") == null) {
            throw new DriverNotFoundException("Web drive path not define");
        }
        Thread fileSourceThread = new Thread((FileSource) fileSource, "FileSourceThread");

        LOG.info("Starting file source service");
        fileSourceThread.start();
    }

    /**
     * Authorize data for vk app
     *
     * @return auth data
     */
    public static AuthorizeData authorizeData() {
        AuthorizeData authorizeData = new AuthorizeData();
        authorizeData.setClientId("5591327");
        authorizeData.setResponseType(ResponseType.TOKEN);
        authorizeData.setDisplay(Display.MOBILE);
        authorizeData.setScope("wall,photos");
        authorizeData.setV(5.33F);
        return authorizeData;
    }

}