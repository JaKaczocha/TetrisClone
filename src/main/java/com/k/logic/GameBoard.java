package com.k.logic;

import com.k.logic.blocks.Block;
import com.k.logic.blocks.BlockGenerator;
import com.k.logic.blocks.RandomBlockGenerator;
import com.k.logic.rotator.BlockRotator;
import com.k.logic.rotator.NextShapeInfo;

import java.awt.*;

public class GameBoard implements Board {

    private final int width;
    private final int height;
    private final BlockGenerator blockGenerator;
    private final BlockRotator blockRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Points points;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        blockGenerator = new RandomBlockGenerator();
        blockRotator = new BlockRotator();
        points = new Points();
    }

    @Override
    public boolean moveBlockDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, blockRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    @Override
    public boolean moveBlockLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, blockRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBlockRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, blockRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBlock() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = blockRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            blockRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    @Override
    public boolean createNewBlock() {
        Block currentBrick = blockGenerator.getBlock();
        blockRotator.setBrick(currentBrick);
        currentOffset = new Point(3, 0);
        return MatrixOperations.intersect(currentGameMatrix, blockRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(blockRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), blockGenerator.getNextBlock().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBlockToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, blockRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public DeleteRow deleteRows() {
        DeleteRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMat();
        return clearRow;

    }

    @Override
    public Points getPoints() {
        return points;
    }


    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        points.reset();
        createNewBlock();
    }
}
