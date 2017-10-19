package com.github.vk.bot.contentservice.endpoint;

import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.model.content.Item;
import com.github.vk.bot.contentservice.service.ContentSourceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

/**
 * Created at 11.10.2017 10:34
 *
 * @author AMarchenkov
 */
@RestController
@RequestMapping(value = "/*", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentController {

    private final ContentSourceService contentSourceService;

    @Autowired
    public ContentController(ContentSourceService contentSourceService) {
        this.contentSourceService = contentSourceService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/item")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(contentSourceService.getItems());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentSource>> getContentSourceList() {
        List<ContentSource> sources = contentSourceService.getAllSources();
        return ResponseEntity.ok(Optional.of(sources).orElse(new ArrayList<>()));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addContentSource(@RequestBody ContentSource contentSource) {
        ObjectId objectId = contentSourceService.saveSource(contentSource);
        return ResponseEntity.created(URI.create("/" + objectId.toString())).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{content_source_id}")
    public ResponseEntity<Void> removeContentSource(@PathVariable("content_source_id") ObjectId id) {
        contentSourceService.removeContentSource(id);
        return ResponseEntity.noContent().build();
    }
}
