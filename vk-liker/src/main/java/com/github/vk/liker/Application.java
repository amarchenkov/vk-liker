package com.github.vk.liker;

import com.github.vk.liker.service.LikeService;
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

    private LikeService likeService;
    private Source fileSource;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Autowired
    @Qualifier("fileSource")
    public void setFileSource(Source fileSource) {
        this.fileSource = fileSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread likerThread = new Thread(likeService, "LikeServiceThread");
        Thread fileSourceThread = new Thread((FileSource) fileSource, "FileSourceThread");

        LOG.info("Starting file source service");
        fileSourceThread.start();

        LOG.info("Starting like service");
        likerThread.start();
    }

}