package com.heidiaandahl.entity;

import java.time.LocalDate;
import java.util.Objects;

public class GoalsDescription {
    private int id;
    private String description;

    public GoalsDescription() {
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
        GoalsDescription that = (GoalsDescription) o;
        return id == that.id &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "GoalsDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
