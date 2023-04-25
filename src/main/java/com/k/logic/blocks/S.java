package com.k.logic.blocks;

import com.k.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class S implements Block {

    private final List<int[][]> blockMatrix = new ArrayList<>();

    public S() {
        blockMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        blockMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(blockMatrix);
    }
}
