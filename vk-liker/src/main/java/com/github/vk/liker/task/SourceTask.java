package com.github.vk.liker.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created at 30.08.2016 10:10
 *
 * @author AMarchenkov
 */
public class SourceTask {

    private UUID uuid;
    private List<Long> idList = new ArrayList<>();

    /**
     *
     * @param uuid
     */
    public SourceTask(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @param uuid
     * @param items
     */
    public SourceTask(UUID uuid, List<Long> items) {
        this(uuid);
        this.idList = items;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Long> getIdList() {
        return idList;
    }

}
