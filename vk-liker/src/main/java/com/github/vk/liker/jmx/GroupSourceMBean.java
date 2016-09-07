package com.github.vk.liker.jmx;

/**
 * Created at 28.08.2016 19:58
 *
 * @author Andrey
 */
@FunctionalInterface
public interface GroupSourceMBean {
    /**
     * Start like processing for group members
     *
     * @param groupId Group ID
     */
    void start(long groupId);
}
