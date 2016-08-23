package com.github.vk.liker.service;

import com.github.vk.liker.enums.WatcherStatus;

/**
 * Created at 23.08.2016 12:16
 *
 * @author AMarchenkov
 */
public interface LikeWatcher {
    void start();
    void stop();
    WatcherStatus getStatus();
}
