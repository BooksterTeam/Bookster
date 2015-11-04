package io.github.bookster.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Copy.
 */

@Document(collection = "copy")
public class Copy implements Serializable {

    @Id
    private String id;

    @Field("verified")
    private Boolean verified;

    @Field("available")
    private Boolean available;

    @DBRef
    private Book book;

    public Copy(Boolean available, Boolean verified, Book book) {
        this.available = available;
        this.verified = verified;
        this.book = book;
    }

    public Copy(String id, Boolean available, Boolean verified, Book book) {
        this.id = id;
        this.available = available;
        this.verified = verified;
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Copy copy = (Copy) o;

        if ( ! Objects.equals(id, copy.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Copy{" +
            "id=" + id +
            ", verified='" + verified + "'" +
            ", available='" + available + "'" +
            '}';
    }
}
