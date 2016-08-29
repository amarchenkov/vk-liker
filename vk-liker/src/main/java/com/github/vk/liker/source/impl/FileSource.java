package com.github.vk.liker.source.impl;

import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.source.Source;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created at 29.08.2016 13:11
 *
 * @author AMarchenkov
 */
@Service
@ConfigurationProperties(prefix = "liker.file-watcher")
public class FileSource implements Source, Runnable {

    private static final Logger LOG = LogManager.getLogger(Source.class);

    private Integer delay;

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    private LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public List<Long> getList() {
        List<Long> result = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Paths.get("data.txt"));
            lines.filter(e -> e != null).mapToLong(Long::valueOf).forEach(result::add);
        } catch (IOException e) {
            LOG.error("Cannot read data file", e);
        }
        return result;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Path sourceFilePath = Paths.get("data.txt");
                if (Files.exists(sourceFilePath)) {
                    likeService.addListToProcess(getList());
                    Files.delete(sourceFilePath);
                }
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.error("File watcher thread has been interrupted", e);
            } catch (IOException e) {
                LOG.error("File remove failed", e);
            }
        }
    }

}
