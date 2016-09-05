package com.github.vk.liker.source.impl;

import com.github.vk.api.VK;
import com.github.vk.liker.jmx.GroupSourceMBean;
import com.github.vk.liker.jmx.SearchSourceMBean;
import com.github.vk.liker.service.LikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created at 29.08.2016 13:11
 *
 * @author AMarchenkov
 */
@Service
@ManagedResource(objectName = "Liker:name=SearchSource")
public class SearchSource implements SearchSourceMBean {

    private static final Logger LOG = LogManager.getLogger(GroupSourceMBean.class);

    private LikeService likeService;
    private int fromAge;
    private int toAge;
    private String city;
    private String country;

    @ManagedAttribute
    public int getFromAge() {
        return fromAge;
    }

    @ManagedAttribute
    public int getToAge() {
        return toAge;
    }

    @ManagedAttribute
    public String getCity() {
        return city;
    }

    @ManagedAttribute
    public String getCountry() {
        return country;
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }


    @Override
    @ManagedOperation(description = "Start liking search result")
    public void start() {
        VK vk = new VK();
    }

}
