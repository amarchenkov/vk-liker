package com.github.vk.liker.service.impl;

import com.github.vk.liker.service.LikeWatcher;
import com.github.vk.liker.service.WatcherManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created at 23.08.2016 12:20
 *
 * @author AMarchenkov
 */
@Service("watcherManager")
public class WatcherManagerImpl implements WatcherManager {

    private static final Logger LOG = LogManager.getLogger(WatcherManager.class);

    private List<LikeWatcher> watchers = new CopyOnWriteArrayList<>();

    @PostConstruct
    protected void init() {
        LOG.info("Default watcher manager has been initialized");
    }

    @PreDestroy
    private void finish() {
        LOG.info("Default watcher manager has been initialized");
    }

    @Override
    public void setThreadCount(int count) {
    }

    @Override
    public void add(LikeWatcher watcher) {
        watchers.add(watcher);
        watcher.start();
    }

    @Override
    public void remove(LikeWatcher watcher) {
        watcher.stop();
        watchers.remove(watcher);
    }

    @Override
    public void startAll() {
        watchers.forEach(LikeWatcher::start);
    }

    @Override
    public void stopAll() {
        watchers.forEach(LikeWatcher::stop);
    }

}