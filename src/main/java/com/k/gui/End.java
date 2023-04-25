package com.k.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class End extends BorderPane {

    public End() {
        final Label endLabel = new Label("THE END");
        endLabel.getStyleClass().add("endStyle");
        setCenter(endLabel);
    }

}
