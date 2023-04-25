package com.k.logic;

public final class DeleteRow {

    private final int linesR;
    private final int[][] newMat;
    private final int pointsBonus;

    public DeleteRow(int linesRemoved, int[][] newMatrix, int pointsBonus) {
        this.linesR = linesRemoved;
        this.newMat = newMatrix;
        this.pointsBonus = pointsBonus;
    }

    public int getLinesR() {
        return linesR;
    }

    public int[][] getNewMat() {
        return MatrixOperations.copy(newMat);
    }

    public int getPointsBonus() {
        return pointsBonus;
    }
}
