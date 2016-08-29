package com.github.vk.liker.source.impl;

import com.github.vk.api.VK;
import com.github.vk.api.models.json.GroupMembersResponse;
import com.github.vk.liker.jmx.GroupSourceMBean;
import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.source.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created at 29.08.2016 13:12
 *
 * @author AMarchenkov
 */
@Service
public class GroupSource implements GroupSourceMBean {

    private LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public void start(long groupId) {
        VK vk = new VK();
        vk.getGroupMembers(groupId).ifPresent(this::sendToProcessing);
    }

    private void sendToProcessing(GroupMembersResponse members) {
        likeService.addListToProcess(members.getItems());
    }

}
