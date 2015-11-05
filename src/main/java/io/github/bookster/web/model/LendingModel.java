package io.github.bookster.web.model;

import io.github.bookster.domain.Copy;
import io.github.bookster.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Lending.
 */

public class LendingModel implements Serializable {

    private String id;

    private String from;

    private String due;
    
    private Copy copy;

    private User user;

    public LendingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LendingModel lendingModel = (LendingModel) o;

        if ( ! Objects.equals(id, lendingModel.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lending{" +
            "id=" + id +
            ", from='" + from + "'" +
            ", due='" + due + "'" +
            ", copy='" + copy+"'"+ 
            ", borrower='" + user+"'}";
    }
}
