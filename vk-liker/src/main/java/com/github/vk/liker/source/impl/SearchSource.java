package com.github.vk.liker.source.impl;

import com.github.vk.liker.jmx.SearchSourceMBean;
import com.github.vk.liker.source.Source;

import java.util.List;

/**
 * Created at 29.08.2016 13:11
 *
 * @author AMarchenkov
 */
public class SearchSource implements Source, SearchSourceMBean {

    @Override
    public List<Long> getList() {
        return null;
    }

}
