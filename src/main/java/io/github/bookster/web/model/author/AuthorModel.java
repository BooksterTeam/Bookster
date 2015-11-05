package io.github.bookster.web.model.author;

import java.util.*;

/**
 * A Author.
 */

public class AuthorModel {

    private String id;

    private String forename;

    private String surname;

    private List<BookDataModel> books = new ArrayList<>();

    public AuthorModel() {
    }

    public AuthorModel(String id, String forename, String surname, List<BookDataModel> books) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.books = books;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<BookDataModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookDataModel> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorModel author = (AuthorModel) o;

        if ( ! Objects.equals(id, author.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + id +
            ", forename='" + forename + "'" +
            ", surname='" + surname + "'" +
            ", books='" + books+ '}';
    }
}
