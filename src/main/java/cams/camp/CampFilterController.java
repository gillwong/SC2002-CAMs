package cams.camp;

import cams.filter.FilterStrategy;
import java.util.ArrayList;

public class CampFilterController {
    private static CampFilterController campFilterController;
    private ArrayList<FilterStrategy> filterStrategies;

    private CampFilterController() {}

    public static CampFilterController getInstance() {
        if(campFilterController == null){
            campFilterController = new CampFilterController();
        }
        return campFilterController;
    }

    public void addFilterStrategy(FilterStrategy strategy) {
        filterStrategies.add(strategy);
    }

    public ArrayList<FilterStrategy> getFilterStrategies() {
        return filterStrategies;
    }

    public ArrayList<Camp> filter(String faculty) {
        ArrayList<Camp> filteredList = new ArrayList<Camp>();
        CampController campController = CampController.getInstance();
        ArrayList<Camp> allCamps = campController.getAllCamps();
        for(Camp camp:allCamps) {
            if(camp.getUserGroup() == faculty)
                filteredList.add(camp);
        }
        for(FilterStrategy strategy:filterStrategies) {
            strategy.filter(filteredList);
        }
        return filteredList;
    }

}
