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

    public SourceTask(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
