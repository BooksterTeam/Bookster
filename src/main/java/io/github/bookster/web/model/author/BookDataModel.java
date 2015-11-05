package io.github.bookster.web.model.author;

/**
 * Created on 05/11/15
 * author: nixoxo
 */
public class BookDataModel {

    private String title;

    private String id;

    public BookDataModel() {
    }

    public BookDataModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
