package com.github.vk.liker;

import com.github.vk.liker.service.WatcherManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created at 17.08.2016 10:51
 *
 * @author AMarchenkov
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOG = LogManager.getLogger(Application.class);

    private WatcherManager watcherManager;

    @Autowired
    public void setWatcherManager(WatcherManager watcherManager) {
        this.watcherManager = watcherManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        watcherManager.startAll();
    }

}