package io.github.bookster.web.model.book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 04/11/15
 * author: nixoxo
 */
public class BookModel {

    private String id;

    private String isbn;

    private String title;

    private Boolean verified;

    private String published;

    private String subtitle;

    private String tag;

    private List<AuthorDataModel> authors = new ArrayList<>();

    private String author;

    private List<CopyDataModel> copies = new ArrayList<>();

    public BookModel() {
    }

    public BookModel(String id, String isbn, String title, String published, String subtitle, String tag, List<AuthorDataModel> authors, List<CopyDataModel> copies) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.subtitle = subtitle;
        this.tag = tag;
        this.authors = authors;
        this.copies = copies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public List<AuthorDataModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDataModel> authors) {
        this.authors = authors;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<CopyDataModel> getCopies() {
        return copies;
    }

    public void setCopies(List<CopyDataModel> copies) {
        this.copies = copies;
    }
}
