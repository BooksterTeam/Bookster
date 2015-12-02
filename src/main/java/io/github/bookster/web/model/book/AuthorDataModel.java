package io.github.bookster.web.model.book;

/**
 * Created on 05/11/15
 * author: nixoxo
 */
public class AuthorDataModel {

    private String id;
    private String name;

    public AuthorDataModel() {
    }

    public AuthorDataModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
