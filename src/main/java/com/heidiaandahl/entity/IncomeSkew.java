package com.heidiaandahl.entity;

import java.util.Objects;

public class IncomeSkew {
    private int id;
    private String description;

    public IncomeSkew() {
    }

    public IncomeSkew(String description) {
        this.description = description;
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
        IncomeSkew that = (IncomeSkew) o;
        return id == that.id &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "IncomeSkew{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
