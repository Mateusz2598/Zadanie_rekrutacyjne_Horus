import Interface.Block;
import Interface.CompositeBlock;
import Interface.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {

    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return findBlockInColor(blocks, color);
    }

    private Optional<Block> findBlockInColor(List<Block> blocks, String color) {
        for (Block b : blocks) {
            if (b.getColor().equals(color)) {
                return Optional.of(b);
            }
            if (b instanceof CompositeBlock) {
                Optional<Block> nestedBlock = findBlockInColor(((CompositeBlock) b).getBlocks(), color);
                if (nestedBlock.isPresent()) {
                    return nestedBlock;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return findBlocksInComposite(blocks, material);
    }

    private List<Block> findBlocksInComposite(List<Block> blocks, String material) {
        List<Block> result = new ArrayList<>();
        for (Block b : blocks) {
            if (b.getMaterial().equals(material)) {
                result.add(b);
            }
            if (b instanceof CompositeBlock) {
                List<Block> nestedBlocks = findBlocksInComposite(((CompositeBlock) b).getBlocks(), material);
                result.addAll(nestedBlocks);
            }
        }
        return result;
    }

    @Override
    public int count() {
        return countBlocks(blocks);
    }

    private int countBlocks(List<Block> blocks) {
        int count = 0;
        for (Block b : blocks) {
            count++;
            if (b instanceof CompositeBlock) {
                count += countBlocks(((CompositeBlock) b).getBlocks());
            }
        }
        return count;
    }
}
