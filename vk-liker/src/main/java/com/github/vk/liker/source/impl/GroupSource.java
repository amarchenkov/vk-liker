package com.github.vk.liker.source.impl;

import com.github.vk.api.VK;
import com.github.vk.api.models.json.GroupMembersResponse;
import com.github.vk.liker.jmx.GroupSourceMBean;
import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.task.SourceTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created at 29.08.2016 13:12
 *
 * @author AMarchenkov
 */
@Service
@ManagedResource(objectName = "Liker:name=GroupSource")
public class GroupSource implements GroupSourceMBean {

    private static final Logger LOG = LogManager.getLogger(GroupSourceMBean.class);

    private LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    @ManagedOperation(description = "Start group liking")
    @ManagedOperationParameter(name = "groupId", description = "ID of VK group")
    public void start(long groupId) {
        LOG.info("Start group[{}] member like process via JMX", groupId);
        VK vk = new VK();
        vk.getGroupMembers(groupId).ifPresent(this::sendToProcessing);
    }

    private void sendToProcessing(GroupMembersResponse members) {
        likeService.addListToProcess(new SourceTask(UUID.randomUUID(), members.getItems()));
    }

}
