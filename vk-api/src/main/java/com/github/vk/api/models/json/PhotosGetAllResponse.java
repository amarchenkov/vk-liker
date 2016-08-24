package com.github.vk.api.models.json;

import java.util.List;

/**
 * Created at 24.08.2016 11:23
 *
 * @author AMarchenkov
 */
public class PhotosGetAllResponse {

    private long count;
    private List<PhotoItem> items;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }
}
