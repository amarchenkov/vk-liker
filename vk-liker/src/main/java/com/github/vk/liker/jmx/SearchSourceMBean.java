package com.github.vk.liker.jmx;

/**
 * Created at 28.08.2016 19:58
 *
 * @author Andrey
 */
@FunctionalInterface
public interface SearchSourceMBean {
    /**
     * Start getting search result with params in property
     */
    void start();
}
