package com.github.vk.bot.contentservice.service.impl;

import com.github.vk.bot.common.model.content.Item;
import com.vk.api.sdk.objects.wall.WallPostFull;
import org.springframework.stereotype.Service;

/**
 * Created at 06.10.2017 12:14
 *
 * @author AMarchenkov
 */
@Service
public class ModelConverter {
    public Item fromVkItemToMongoItem(WallPostFull wallPostFull) {
        Item result = new Item();
//        result.setCanPin(wallPostFull.getCanPin());
//        result.setDate(wallPostFull.getDate());
//        result.setText(wallPostFull.getText());
//        result.setSourceId(wallPostFull.getId());
//        result.setOwnerId(wallPostFull.getOwnerId());
//        result.setFromId(wallPostFull.getFromId());
//        result.setPostType(wallPostFull.getPostType());
//        result.setMarkedAsAds(wallPostFull.);
//        result.setContentSourceId();
//        result.setAttachments();
        return null;
    }
}
