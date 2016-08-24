package com.github.vk.api.models.json;

import java.util.List;

/**
 * Created at 24.08.2016 10:43
 *
 * @author AMarchenkov
 */
public class WallGetResponse {

    private long count;
    private List<WallItem> items;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<WallItem> getItems() {
        return items;
    }

    public void setItems(List<WallItem> items) {
        this.items = items;
    }
}
