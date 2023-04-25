package com.k.gui;

import com.k.logic.DownData;
import com.k.logic.ViewData;
import com.k.logic.events.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BLOCK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Text pointsValue;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane nextBlock;

    @FXML
    private GridPane blockPanel;

    @FXML
    private ToggleButton pauseButton;

    @FXML
    private End gameOverPanel;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    
    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    
    private Rectangle[][] rectangles;

    private Timeline timeLine;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("font.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBlock(eventListener.onLeftEvent(new MoveEvent(Type.LEFT, Source.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBlock(eventListener.onRightEvent(new MoveEvent(Type.RIGHT, Source.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBlock(eventListener.onRotateEvent(new MoveEvent(Type.ROTATE, Source.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(Type.DOWN, Source.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());
                }

            }
        });
        gameOverPanel.setVisible(false);
        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timeLine.pause();
                    pauseButton.setText("Resume");
                } else {
                    timeLine.play();
                    pauseButton.setText("Pause");
                }
            }
        });
    }

    public void initGameView(int[][] boardMatrix, ViewData block) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[block.getBlockData().length][block.getBlockData()[0].length];
        for (int i = 0; i < block.getBlockData().length; i++) {
            for (int j = 0; j < block.getBlockData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rectangle.setFill(getFillColor(block.getBlockData()[i][j]));
                rectangles[i][j] = rectangle;
                blockPanel.add(rectangle, j, i);
            }
        }
        blockPanel.setLayoutX(gamePanel.getLayoutX() + block.getxPos() * blockPanel.getVgap() + block.getxPos() * BLOCK_SIZE);
        blockPanel.setLayoutY(-42 + gamePanel.getLayoutY() + block.getyPos() * blockPanel.getHgap() + block.getyPos() * BLOCK_SIZE);

        generatePreviewPanel(block.getNextBlockData());


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(300),
                ae -> moveDown(new MoveEvent(Type.DOWN, Source.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private Paint getFillColor(int j) {
        Paint color;
        switch (j) {
            case 0:
                color = Color.TRANSPARENT;
                break;
            case 1:
                color = Color.AQUA;
                break;
            case 2:
                color = Color.GREY;
                break;
            case 3:
                color = Color.BLUEVIOLET;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            case 5:
                color = Color.GREEN;
                break;
            case 6:
                color = Color.BEIGE;
                break;
            case 7:
                color = Color.DEEPPINK;
                break;
            default:
                color = Color.BURLYWOOD;
                break;
        }
        return color;
    }

    private void generatePreviewPanel(int[][] nextBlockData) {
        nextBlock.getChildren().clear();
        for (int i = 0; i < nextBlockData.length; i++) {
            for (int j = 0; j < nextBlockData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                setRectangleData(nextBlockData[i][j], rectangle);
                if (nextBlockData[i][j] != 0) {
                    nextBlock.add(rectangle, j, i);
                }
            }
        }
    }

    private void refreshBlock(ViewData block) {

        if (isPause.getValue() == Boolean.FALSE) {
            blockPanel.setLayoutX(gamePanel.getLayoutX() + block.getxPos() * blockPanel.getVgap() + block.getxPos() * BLOCK_SIZE);
            blockPanel.setLayoutY(-42 + gamePanel.getLayoutY() + block.getyPos() * blockPanel.getHgap() + block.getyPos() * BLOCK_SIZE);
            for (int i = 0; i < block.getBlockData().length; i++) {
                for (int j = 0; j < block.getBlockData()[i].length; j++) {
                    setRectangleData(block.getBlockData()[i][j], rectangles[i][j]);
                }
            }
            generatePreviewPanel(block.getNextBlockData());
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(2);
        rectangle.setArcWidth(2);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesR() > 0) {
                Information notificationPanel = new Information("+" + downData.getClearRow().getPointsBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showPoints(groupNotification.getChildren());
            }
            refreshBlock(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindPoints(IntegerProperty integerProperty) {
        pointsValue.textProperty().bind(integerProperty.asString());
    }

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);

    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }
}
