package com.k.logic.blocks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBlockGenerator implements BlockGenerator {

    private final List<Block> blockList;

    private final Deque<Block> nextBlocks = new ArrayDeque<>();

    public RandomBlockGenerator() {
        blockList = new ArrayList<>();
        blockList.add(new I());
        blockList.add(new J());
        blockList.add(new L());
        blockList.add(new O());
        blockList.add(new S());
        blockList.add(new T());
        blockList.add(new Z());
        Block currentBlock = blockList.get(ThreadLocalRandom.current().nextInt(blockList.size()));
        nextBlocks.add(currentBlock);
        while (true) {
            Block nextBlock = blockList.get(ThreadLocalRandom.current().nextInt(blockList.size()));
            if (nextBlock.getClass() != currentBlock.getClass()) {
                nextBlocks.add(nextBlock);
                break;
            }
        }
        nextBlocks.add(blockList.get(ThreadLocalRandom.current().nextInt(blockList.size())));
    }

    @Override
    public Block getBlock() {
        if (nextBlocks.size() <= 1) {
            nextBlocks.add(blockList.get(ThreadLocalRandom.current().nextInt(blockList.size())));
        }
        return nextBlocks.poll();
    }

    @Override
    public Block getNextBlock() {
        return nextBlocks.peek();
    }
}
