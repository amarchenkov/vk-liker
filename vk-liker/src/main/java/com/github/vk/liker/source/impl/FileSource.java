package com.github.vk.liker.source.impl;

import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.source.Source;
import com.github.vk.liker.task.SourceTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created at 29.08.2016 13:11
 *
 * @author AMarchenkov
 */
@Service
@ConfigurationProperties(prefix = "liker.file-watcher")
public class FileSource implements Source, Runnable {

    private static final String FILE_NAME = "data.txt";
    private static final Logger LOG = LogManager.getLogger(Source.class);

    private int delay;
    private LikeService likeService;

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public SourceTask getList() {
        SourceTask result = new SourceTask(UUID.randomUUID());
        try (Stream<String> lines = Files.lines(Paths.get(FILE_NAME))) {
            lines.filter(e -> e != null && !e.isEmpty()).mapToLong(Long::valueOf).forEach(result.getIdList()::add);
        } catch (IOException e) {
            LOG.error("Cannot read data file", e);
        }
        return result;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Path sourceFilePath = Paths.get(FILE_NAME);
                if (Files.exists(sourceFilePath)) {
                    likeService.addListToProcess(getList());
                    Files.delete(sourceFilePath);
                }
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.error("File watcher thread has been interrupted", e);
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                LOG.error("File remove failed", e);
            }
        }
    }

}
