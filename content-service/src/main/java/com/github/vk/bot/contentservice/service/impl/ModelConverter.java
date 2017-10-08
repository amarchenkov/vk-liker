package com.github.vk.bot.contentservice.service.impl;

import com.github.vk.bot.common.enums.AttachmentType;
import com.github.vk.bot.common.enums.PostType;
import com.github.vk.bot.common.model.content.Attachment;
import com.github.vk.bot.common.model.content.Item;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created at 06.10.2017 12:14
 *
 * @author AMarchenkov
 */
@Component
public class ModelConverter {

    //TODO NullPointerException Fix
    public Item fromVkItemToMongoItem(WallPostFull wallPostFull) {
        Item result = new Item();
        result.setDate(wallPostFull.getDate());
        result.setText(wallPostFull.getText());
        result.setSourceId(wallPostFull.getId());
        result.setOwnerId(wallPostFull.getOwnerId());
        result.setFromId(wallPostFull.getFromId());
        result.setSourceId(wallPostFull.getId());
        result.setPostType(PostType.valueOf(wallPostFull.getPostType().toString()));
        if (wallPostFull.getAttachments() != null) {
            result.setAttachments(convertAttachments(wallPostFull.getAttachments()));
        }
        return result;
    }

    private Set<Attachment> convertAttachments(List<WallpostAttachment> attachments) {
        return attachments.stream()
                .filter(wallPostAttachment -> wallPostAttachment.getType().equals(WallpostAttachmentType.PHOTO))
                .map(wallPostAttachment -> {
                    Attachment attachment = new Attachment();
                    attachment.setAttachmentType(AttachmentType.valueOf(wallPostAttachment.getType().toString()));
                    attachment.setPhoto(convertPhoto(wallPostAttachment.getPhoto()));
                    return attachment;
                })
                .collect(Collectors.toSet());
    }

    private com.github.vk.bot.common.model.content.Photo convertPhoto(Photo photo) {
        com.github.vk.bot.common.model.content.Photo result = new com.github.vk.bot.common.model.content.Photo();
        result.setAccessKey(photo.getAccessKey());
        result.setAlbumId(photo.getAlbumId());
        result.setDate(photo.getDate());
        result.setHeight(photo.getHeight());
        result.setOwnerId(photo.getOwnerId());
        result.setPhoto75(photo.getPhoto75());
        result.setPhoto130(photo.getPhoto130());
        result.setPhoto604(photo.getPhoto604());
        result.setPhoto807(photo.getPhoto807());
        result.setPhoto1280(photo.getPhoto1280());
        result.setSourceId(photo.getId());
        result.setText(photo.getText());
        result.setUserId(photo.getUserId());
        result.setWidth(photo.getWidth());
        return result;
    }

}
