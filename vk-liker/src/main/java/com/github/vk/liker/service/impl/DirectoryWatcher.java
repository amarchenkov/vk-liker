package com.github.vk.liker.service.impl;

import com.github.vk.liker.enums.WatcherStatus;
import com.github.vk.liker.service.LikeWatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created at 23.08.2016 12:15
 *
 * @author AMarchenkov
 */
@Service
public class DirectoryWatcher implements LikeWatcher, Runnable {

    private static final Logger LOG = LogManager.getLogger(LikeWatcher.class);

    private WatcherStatus status;

    @Override
    public void start() {
        status = WatcherStatus.STARTED;
    }

    @Override
    public void stop() {
        status = WatcherStatus.STOPPED;
    }

    @Override
    public WatcherStatus getStatus() {
        return status;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                status = WatcherStatus.FAILED;
                LOG.error("Directory watcher interrupted", e);
            }
        }
        status = WatcherStatus.STOPPED;
    }
}