package com.github.vk.liker.service;

import java.util.List;

/**
 * Created at 29.08.2016 13:09
 *
 * @author AMarchenkov
 */
public interface LikeService extends Runnable {
    /**
     * Add account id list to queue for processing
     *
     * @param list list of owner id
     */
    void addListToProcess(List<Long> list);
}