package com.github.vk.api.models.json;

import java.util.List;

/**
 * Created at 01.09.2016 8:06
 *
 * @author Andrey
 */
public class UsersSearchResponse {
    private int count;
    private List<UsersSearchItem> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UsersSearchItem> getItems() {
        return items;
    }

    public void setItems(List<UsersSearchItem> items) {
        this.items = items;
    }
}
