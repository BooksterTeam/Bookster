package io.github.bookster.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Book.
 */

@Document(collection = "book")
public class Book implements Serializable {

    @Id
    private String id;

    @Field("isbn")
    private String isbn;

    @Field("title")
    private String title;

    @Field("verified")
    private Boolean verified;

    @Field("published")
    private String published;

    @Field("subtitle")
    private String subtitle;

    @DBRef
    private Set<Tag> tags = new HashSet<>();

    @DBRef
    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

    public Book(String id, String isbn, String title, Boolean verified, String published, String subtitle) {
        this.id = id;
        this.isbn = isbn;
        this.published = published;
        this.subtitle = subtitle;
        this.title = title;
        this.verified = verified;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        if (!Objects.equals(id, book.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + "'" +
                ", title='" + title + "'" +
                ", verified='" + verified + "'" +
                ", published='" + published + "'" +
                ", subtitle='" + subtitle + "'" +
                ", tags='" + tags + "'" +
                ", authors='" + authors+"'}";
    }
}
