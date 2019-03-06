package com.heidiaandahl.entity;

import java.util.Objects;

public class NeedsDescription {
    private int id;
    private String description;

    public NeedsDescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeedsDescription that = (NeedsDescription) o;
        return id == that.id &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "NeedsDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
