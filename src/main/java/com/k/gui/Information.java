package com.k.gui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Information extends BorderPane {

    public Information(String text) {
        setMinHeight(200);
        setMinWidth(220);
        final Label points = new Label(text);
        points.getStyleClass().add("bonusStyle");
        final Effect glow = new Glow(0.6);
        points.setEffect(glow);

        points.setTextFill(Color.WHITE);
        setCenter(points);

    }

    public void showPoints(ObservableList<Node> list) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1900), this);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2400), this);
        translateTransition.setToY(this.getLayoutY() - 40);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        ParallelTransition transition = new ParallelTransition(translateTransition, fadeTransition);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(Information.this);
            }
        });
        transition.play();
    }
}
