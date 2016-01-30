package net.han;

import org.bukkit.block.Block;

/**
 * Created by han on 2016/1/30.
 */
public class LocCub {
    int X ;

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }

    int Y;
    int Z;
    Block block;

    public LocCub() {
    }

    public LocCub(int xPoint, int yPoint, int zPoint, Block currentBlock) {
        X=xPoint;Y=yPoint;Z=zPoint;block=currentBlock;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }





}
