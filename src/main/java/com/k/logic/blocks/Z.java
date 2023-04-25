package com.k.logic.blocks;

import com.k.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class Z implements Block {

    private final List<int[][]> blockMatrix = new ArrayList<>();

    public Z() {
        blockMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        blockMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(blockMatrix);
    }
}
