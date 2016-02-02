package rbrewer.rbrewer_fueltrack;

import java.util.ArrayList;

/**
 * Created by Ryan on 2016-02-01.
 */
public class TotalCost {
    private ArrayList<Double> totalCostList;
    private Double totalCost;

    public TotalCost(ArrayList<Double> totalCostList) {
        this.totalCostList = totalCostList;
        this.totalCost = calculateTotalCost();
    }

    private Double calculateTotalCost() {
        totalCost = 0.00;
        for(int i = 0; i < totalCostList.size(); i++) {
            totalCost = totalCost + totalCostList.get(i);
        }
        return totalCost;
    }

    public Double getTotalCost() {
        totalCost = calculateTotalCost();
        return totalCost;
    }
}
