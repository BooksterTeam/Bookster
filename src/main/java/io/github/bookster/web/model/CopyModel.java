package io.github.bookster.web.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Copy.
 */

public class CopyModel implements Serializable{

    private String id;

    private Boolean verified;

    private Boolean available;

    private String book;

    public CopyModel() {
    }

    public CopyModel(Boolean available, Boolean verified) {
        this.available = available;
        this.verified = verified;
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

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
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

        CopyModel copy = (CopyModel) o;

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
            ", book ='" + book+'}';
    }
}
