package com.github.vk.liker.service;

import com.github.vk.liker.enums.WatcherStatus;

/**
 * Created at 23.08.2016 12:18
 *
 * @author AMarchenkov
 */
public interface WatcherManager {
    void setThreadCount(int count);

    void add(LikeWatcher watcher);

    void remove(LikeWatcher watcher);

    void startAll();

    void stopAll();
}
