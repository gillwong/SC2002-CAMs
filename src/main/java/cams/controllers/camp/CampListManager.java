package cams.controllers.camp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cams.controllers.Manager;
import cams.models.CamsData;
import cams.models.camp.Camp;

public class CampListManager extends Manager {
    private final Map<CampFilterType, CampFilter> filters;

    public CampListManager() {
        filters = new HashMap<>();
    }

    public Map<CampFilterType, String> getFilters() {
        Map<CampFilterType, String> res = new HashMap<>();
        for (var filter : filters.entrySet())
            res.put(filter.getKey(), filter.getValue().getCriteria());
        return res;
    }

    public void setFilter(CampFilterType type, String criteria)
            throws IllegalArgumentException, DateTimeParseException {
        DateTimeFormatter formatter = app.getDateFormmatter();
        Predicate<Camp> pred = switch (type) {
            case NAME ->
                camp -> camp.getName().contains(criteria);

            case LOCATION ->
                camp -> camp.getLocation().equalsIgnoreCase(criteria);

            case START_DATE ->
                camp -> camp.getStartDate().isAfter(LocalDate.parse(criteria, formatter));

            case END_DATE ->
                camp -> camp.getEndDate().isBefore(LocalDate.parse(criteria, formatter));

            case VISIBILITY ->
                camp -> camp.isVisible() == !criteria.equalsIgnoreCase("n");

            case USER_GROUP ->
                camp -> camp.getUserGroup().equalsIgnoreCase(criteria);

            default ->
                throw new IllegalArgumentException("Invalid camp filter type");
        };
        filters.put(type, new CampFilter(criteria, pred));
    }

    public void clearFilter(CampFilterType type) {
        filters.remove(type);
    }

    public void clearAllFilters() {
        filters.clear();
    }

    public List<Camp> getCamps() {
        Stream<Camp> campStream = CamsData.getInstance().getAllCamps().stream();
        for (CampFilter campFilter : filters.values())
            campStream.filter(campFilter.getPredicate());
        return campStream.collect(Collectors.toCollection(ArrayList::new));
    }
}
