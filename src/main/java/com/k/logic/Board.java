package com.k.logic;

public interface Board {

    boolean moveBlockDown();

    boolean moveBlockLeft();

    boolean moveBlockRight();

    boolean rotateLeftBlock();

    boolean createNewBlock();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBlockToBackground();

    DeleteRow deleteRows();

    Points getPoints();

    void newGame();
}
