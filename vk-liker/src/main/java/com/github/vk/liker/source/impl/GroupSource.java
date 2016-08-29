package com.github.vk.liker.source.impl;

import com.github.vk.liker.jmx.GroupSourceMBean;
import com.github.vk.liker.source.Source;

import java.util.List;

/**
 * Created at 29.08.2016 13:12
 *
 * @author AMarchenkov
 */
public class GroupSource implements Source, GroupSourceMBean {

    @Override
    public List<Long> getList() {
        return null;
    }

}
