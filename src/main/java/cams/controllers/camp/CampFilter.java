package cams.controllers;

import java.util.Objects;
import java.util.function.Predicate;

import cams.models.camp.Camp;

public class CampFilter {
    private final Predicate<Camp> predicate;
    private final String criteria;

    public CampFilter(String criteria, Predicate<Camp> predicate) {
        this.criteria = Objects.requireNonNullElse(criteria, "");
        this.predicate = Objects.requireNonNull(predicate, "Predicate must not be null");
    }

    public Predicate<Camp> getPredicate() {
        return predicate;
    }

    public String getCriteria() {
        return criteria;
    }
}
