package com.k.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Points {

    private final IntegerProperty points = new SimpleIntegerProperty(0);

    public IntegerProperty pointsProperty() {
        return points;
    }


    public void add(int j){
        points.setValue(points.getValue() + j);
    }

    public void reset() {
        points.setValue(0);
    }
}
