package io.github.bookster.web.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Lending.
 */

public class LendingModel implements Serializable {

    private String id;

    private String from;

    private String due;

    private String copi;

    private String user;

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

    public String getCopi() {
        return copi;
    }

    public void setCopi(String copi) {
        this.copi = copi;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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

        if (!Objects.equals(id, lendingModel.id)) return false;

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
                ", copi='" + copi + "'" +
                ", borrower='" + user + "'}";
    }
}
