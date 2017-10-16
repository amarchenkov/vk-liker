package com.github.vk.bot.contentservice.service;

import com.github.vk.bot.common.model.content.ContentSource;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created at 11.10.2017 10:42
 *
 * @author AMarchenkov
 */
public interface ContentSourceService {
    List<ContentSource> getAllSources();
    ObjectId saveSource(ContentSource contentSource);
    void removeContentSource(ObjectId id);
}
