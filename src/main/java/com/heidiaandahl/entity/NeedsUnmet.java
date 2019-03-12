package com.heidiaandahl.entity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

public class NeedsUnmet {
    private int id;
    private boolean food;
    private boolean housing;
    private boolean utilities;
    private boolean healthcare;
    private boolean clothing;
    private boolean transportation;
    private boolean childcare;
    private boolean other;

    @OneToOne(fetch = FetchType.EAGER)
    private Survey survey;

    public NeedsUnmet() {
    }

    public NeedsUnmet(boolean food, boolean housing, boolean utilities, boolean healthcare, boolean clothing,
                      boolean transportation, boolean childcare, boolean other, Survey survey) {
        this.food = food;
        this.housing = housing;
        this.utilities = utilities;
        this.healthcare = healthcare;
        this.clothing = clothing;
        this.transportation = transportation;
        this.childcare = childcare;
        this.other = other;
        this.survey = survey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isHousing() {
        return housing;
    }

    public void setHousing(boolean housing) {
        this.housing = housing;
    }

    public boolean isUtilities() {
        return utilities;
    }

    public void setUtilities(boolean utilities) {
        this.utilities = utilities;
    }

    public boolean isHealthcare() {
        return healthcare;
    }

    public void setHealthcare(boolean healthcare) {
        this.healthcare = healthcare;
    }

    public boolean isClothing() {
        return clothing;
    }

    public void setClothing(boolean clothing) {
        this.clothing = clothing;
    }

    public boolean isTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    public boolean isChildcare() {
        return childcare;
    }

    public void setChildcare(boolean childcare) {
        this.childcare = childcare;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeedsUnmet that = (NeedsUnmet) o;
        return id == that.id &&
                food == that.food &&
                housing == that.housing &&
                utilities == that.utilities &&
                healthcare == that.healthcare &&
                clothing == that.clothing &&
                transportation == that.transportation &&
                childcare == that.childcare &&
                other == that.other;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, food, housing, utilities, healthcare, clothing, transportation, childcare, other);
    }

    @Override
    public String toString() {
        return "NeedsUnmet{" +
                "id=" + id +
                ", food=" + food +
                ", housing=" + housing +
                ", utilities=" + utilities +
                ", healthcare=" + healthcare +
                ", clothing=" + clothing +
                ", transportation=" + transportation +
                ", childcare=" + childcare +
                ", other=" + other +
                '}';
    }
}
