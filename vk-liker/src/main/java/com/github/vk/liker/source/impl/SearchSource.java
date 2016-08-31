package com.github.vk.liker.source.impl;

import com.github.vk.liker.jmx.SearchSourceMBean;
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

    @Override
    @ManagedOperation(description = "Start liking search result")
    @ManagedOperationParameter(name = "searchParams", description = "Params for searching")
    public void start(Map<String, String> searchParams) {

    }

}
