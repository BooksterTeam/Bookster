package io.github.bookster.web.model.book;

/**
 * Created on 05/11/15
 * author: nixoxo
 */
public class CopyDataModel {

    private String id;

    private boolean available;

    public CopyDataModel() {
    }

    public CopyDataModel(String id, boolean available) {
        this.id = id;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
