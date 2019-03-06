package com.heidiaandahl.entity;

import java.util.Objects;

public class GoalsUnmet {
    private int id;
    private boolean savings;
    private boolean careerEducation;
    private boolean needsQuality;
    private boolean donations;
    private boolean recreation;
    private boolean travel;
    private boolean services;
    private boolean other;

    public GoalsUnmet() {
    }

    public GoalsUnmet(boolean savings, boolean careerEducation, boolean needsQuality, boolean donations,
                      boolean recreation, boolean travel, boolean services, boolean other) {
        this.savings = savings;
        this.careerEducation = careerEducation;
        this.needsQuality = needsQuality;
        this.donations = donations;
        this.recreation = recreation;
        this.travel = travel;
        this.services = services;
        this.other = other;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSavings() {
        return savings;
    }

    public void setSavings(boolean savings) {
        this.savings = savings;
    }

    public boolean isCareerEducation() {
        return careerEducation;
    }

    public void setCareerEducation(boolean careerEducation) {
        this.careerEducation = careerEducation;
    }

    public boolean isNeedsQuality() {
        return needsQuality;
    }

    public void setNeedsQuality(boolean needsQuality) {
        this.needsQuality = needsQuality;
    }

    public boolean isDonations() {
        return donations;
    }

    public void setDonations(boolean donations) {
        this.donations = donations;
    }

    public boolean isRecreation() {
        return recreation;
    }

    public void setRecreation(boolean recreation) {
        this.recreation = recreation;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public boolean isServices() {
        return services;
    }

    public void setServices(boolean services) {
        this.services = services;
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
        GoalsUnmet that = (GoalsUnmet) o;
        return id == that.id &&
                savings == that.savings &&
                careerEducation == that.careerEducation &&
                needsQuality == that.needsQuality &&
                donations == that.donations &&
                recreation == that.recreation &&
                travel == that.travel &&
                services == that.services &&
                other == that.other;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, savings, careerEducation, needsQuality, donations, recreation, travel, services, other);
    }

    @Override
    public String toString() {
        return "GoalsUnmet{" +
                "id=" + id +
                ", savings=" + savings +
                ", careerEducation=" + careerEducation +
                ", needsQuality=" + needsQuality +
                ", donations=" + donations +
                ", recreation=" + recreation +
                ", travel=" + travel +
                ", services=" + services +
                ", other=" + other +
                '}';
    }
}
