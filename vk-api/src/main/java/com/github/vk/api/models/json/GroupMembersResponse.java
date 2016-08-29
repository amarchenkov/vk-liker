package com.github.vk.api.models.json;

import java.util.List;

/**
 * Created at 29.08.2016 17:25
 *
 * @author AMarchenkov
 */
public class GroupMembersResponse {
    private long count;
    private List<Long> items;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Long> getItems() {
        return items;
    }

    public void setItems(List<Long> items) {
        this.items = items;
    }
}
