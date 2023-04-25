package com.k.logic;

import com.k.gui.GuiController;
import com.k.logic.events.Source;
import com.k.logic.events.InputEventListener;
import com.k.logic.events.MoveEvent;

public class GameFlow implements InputEventListener {

    private Board board = new GameBoard(23, 10);

    private final GuiController viewGuiController;

    public GameFlow(GuiController c) {
        viewGuiController = c;
        board.createNewBlock();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindPoints(board.getPoints().pointsProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBlockDown();
        DeleteRow clearRow = null;
        if (!canMove) {
            board.mergeBlockToBackground();
            clearRow = board.deleteRows();
            if (clearRow.getLinesR() > 0) {
                board.getPoints().add(clearRow.getPointsBonus());
            }
            if (board.createNewBlock()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == Source.USER) {
                board.getPoints().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBlockLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBlockRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBlock();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
