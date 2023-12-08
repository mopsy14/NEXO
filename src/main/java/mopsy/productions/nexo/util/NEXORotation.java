package mopsy.productions.nexo.util;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;

public enum NEXORotation {
    NORTH(0,0,0, Direction.NORTH,0,0,-1,"north"),
    EAST(0,4.7124f,0, Direction.EAST,1,0,0,"east"),
    SOUTH(0,3.1416f,0, Direction.SOUTH,0,0,1,"south"),
    WEST(0,1.5708f,0, Direction.WEST,-1,0,0,"west"),
    UP(0,-1.5708f,1.5708f, Direction.UP,0,1,0,"up"),
    DOWN(0,-1.5708f,4.7124f, Direction.DOWN,0,-1,0,"down");



    public final float x;
    public final float y;
    public final float z;
    public final Direction direction;
    public final int transformX;
    public final int transformY;
    public final int transformZ;
    public final String id;
    NEXORotation(float x, float y, float z, Direction direction, int transformX, int transformY, int transformZ, String id){
        this.x= x;
        this.y= y;
        this.z= z;
        this.direction= direction;
        this.transformX = transformX;
        this.transformY = transformY;
        this.transformZ = transformZ;
        this.id= id;
    }
    public Vec3f getVec3f(){
        return new Vec3f(x,y,z);
    }
    public static NEXORotation ofDirection(Direction direction){
        switch (direction){
            case UP -> {return UP;}
            case DOWN -> {return DOWN;}
            case NORTH -> {return NORTH;}
            case EAST -> {return EAST;}
            case SOUTH -> {return SOUTH;}
            case WEST -> {return WEST;}
            default -> {return null;}
        }
    }
    public static NEXORotation readPacket(PacketByteBuf buf){
        switch (buf.readInt()){
            case 0 -> {return NORTH;}
            case 1 -> {return EAST;}
            case 2 -> {return SOUTH;}
            case 3 -> {return WEST;}
            case 4 -> {return UP;}
            case 5 -> {return DOWN;}
        }
        return NORTH;
    }
    public void writeToPacket(PacketByteBuf buf){
        if(this == NORTH)
            buf.writeInt(0);
        if(this == EAST)
            buf.writeInt(1);
        if(this == SOUTH)
            buf.writeInt(2);
        if(this == WEST)
            buf.writeInt(3);
        if(this == UP)
            buf.writeInt(4);
        if(this == DOWN)
            buf.writeInt(5);
    }
    public Vec3i transformToVec3i(){
        return new Vec3i(transformX,transformY,transformZ);
    }
}
