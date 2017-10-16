package com.github.vk.bot.contentservice.service.impl;

import com.github.vk.bot.common.enums.SourceType;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.github.vk.bot.contentservice.repository.ItemRepository;
import com.github.vk.bot.contentservice.service.ContentSourceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created at 11.10.2017 10:43
 *
 * @author AMarchenkov
 */
@Service
public class DefaultContentSourceService implements ContentSourceService {

    private final ContentSourceRepository contentSourceRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public DefaultContentSourceService(ContentSourceRepository contentSourceRepository, ItemRepository itemRepository) {
        this.contentSourceRepository = contentSourceRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ContentSource> getAllSources() {
        return contentSourceRepository.findAll();
    }

    @Override
    public ObjectId saveSource(ContentSource contentSource) {
        if (SourceType.VK_GROUP.equals(contentSource.getSourceType()) && contentSource.getSourceId() > 0) {
            contentSource.setSourceId(contentSource.getSourceId() * -1);
        }
        ContentSource saved = contentSourceRepository.save(contentSource);
        return saved.getId();
    }

    @Override
    public void removeContentSource(ObjectId id) {
        itemRepository.deleteByContentSourceId(id);
        contentSourceRepository.delete(id);
    }
}
