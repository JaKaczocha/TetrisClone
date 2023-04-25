package com.k.logic;

public final class ViewData {

    private final int[][] blockData;
    private final int xPos;
    private final int yPos;
    private final int[][] nextBlockData;

    public ViewData(int[][] blockData, int xPosition, int yPosition, int[][] nextBlockData) {
        this.blockData = blockData;
        this.xPos = xPosition;
        this.yPos = yPosition;
        this.nextBlockData = nextBlockData;
    }

    public int[][] getBlockData() {
        return MatrixOperations.copy(blockData);
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int[][] getNextBlockData() {
        return MatrixOperations.copy(nextBlockData);
    }
}
