package com.github.vk.liker.source;

import com.github.vk.liker.task.SourceTask;

import java.util.List;

/**
 * Created at 29.08.2016 12:39
 *
 * @author AMarchenkov
 */
public interface Source {
    /**
     * Get list of owner id from different source
     *
     * @return list of owner id
     */
    SourceTask getList();
}
