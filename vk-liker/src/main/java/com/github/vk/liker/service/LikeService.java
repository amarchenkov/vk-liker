package com.github.vk.liker.service;

import com.github.vk.liker.task.SourceTask;

import java.util.List;

/**
 * Created at 29.08.2016 13:09
 *
 * @author AMarchenkov
 */
public interface LikeService {
    /**
     * Add account id list to queue for processing
     *
     * @param task Pair UUID => ID List
     */
    void addListToProcess(SourceTask task);
}