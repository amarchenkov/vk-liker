package com.github.vk.bot.common.converter;

import com.github.vk.bot.common.enums.AttachmentType;
import com.github.vk.bot.common.enums.Gender;
import com.github.vk.bot.common.enums.PostType;
import com.github.vk.bot.common.model.content.Attachment;
import com.github.vk.bot.common.model.content.Item;
import com.github.vk.bot.common.model.user.GroupUser;
import com.vk.api.sdk.objects.base.Sex;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created at 06.10.2017 12:14
 *
 * @author AMarchenkov
 */
@Component
public class ModelConverter {

    public GroupUser convertToMongoUser(UserXtrCounters user) {
        GroupUser groupUser = new GroupUser();
        if (user.getCountry() != null) {
            groupUser.setCountry(user.getCountry().getTitle());
        }
        groupUser.setFirstName(user.getFirstName());
        groupUser.setLastName(user.getLastName());
        if (user.getSex() != null && !Sex.UNKNOWN.equals(user.getSex())) {
            groupUser.setGender(Sex.FEMALE.equals(user.getSex()) ? Gender.FEMALE : Gender.MALE);
        }
        groupUser.setSourceUserId(user.getId());
        if (user.getCity() != null) {
            groupUser.setState(user.getCity().getTitle());
        }
        return groupUser;
    }

    public Item fromVkItemToMongoItem(WallPostFull wallPostFull) {
        Item result = new Item();
        result.setDate(wallPostFull.getDate());
        result.setText(wallPostFull.getText());
        result.setSourceId(wallPostFull.getId());
        result.setSourceId(wallPostFull.getId());
        result.setPostType(PostType.valueOf(wallPostFull.getPostType().toString()));
        if (wallPostFull.getAttachments() != null) {
            result.setAttachments(convertAttachments(wallPostFull.getAttachments()));
        }
        return result;
    }

    private List<Attachment> convertAttachments(List<WallpostAttachment> attachments) {
        return attachments.stream()
                .filter(wallPostAttachment -> wallPostAttachment.getType().equals(WallpostAttachmentType.PHOTO))
                .filter(wallPostAttachment -> wallPostAttachment.getPhoto().getUserId() != null)
                .map(wallPostAttachment -> {
                    Attachment attachment = new Attachment();
                    attachment.setId(new ObjectId());
                    attachment.setAttachmentType(AttachmentType.valueOf(wallPostAttachment.getType().toString()));
                    attachment.setPhoto(convertPhoto(wallPostAttachment.getPhoto()));
                    return attachment;
                })
                .collect(Collectors.toList());
    }

    private com.github.vk.bot.common.model.content.Photo convertPhoto(Photo photo) {
        com.github.vk.bot.common.model.content.Photo result = new com.github.vk.bot.common.model.content.Photo();
        result.setId(new ObjectId());
        result.setDate(photo.getDate());
        result.setHeight(photo.getHeight());
        result.setOwnerId(photo.getOwnerId());
        result.setPhoto75(photo.getPhoto75());
        result.setPhoto130(photo.getPhoto130());
        result.setPhoto604(photo.getPhoto604());
        result.setPhoto807(photo.getPhoto807());
        result.setPhoto1280(photo.getPhoto1280());
        result.setPhoto2560(photo.getPhoto2560());
        result.setSourceId(photo.getId());
        result.setText(photo.getText());
        result.setUserId(photo.getUserId());
        result.setWidth(photo.getWidth());
        return result;
    }

}
