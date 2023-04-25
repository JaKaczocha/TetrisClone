package com.k.logic.blocks;

import com.k.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class I implements Block {

    private final List<int[][]> blockMatrix = new ArrayList<>();

    public I() {
        blockMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        blockMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(blockMatrix);
    }

}
