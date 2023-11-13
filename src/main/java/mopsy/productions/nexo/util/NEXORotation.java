package mopsy.productions.nexo.util;

import net.minecraft.util.math.Vec3f;

public enum NEXORotation {
    NORTH(0,0,0),
    EAST(0,1.57f,0),
    SOUTH(0,3.14f,0),
    WEST(0,4.71f,0),
    UP(0,0,1.57f),
    DOWN(0,0,4.71f);



    public final float x;
    public final float y;
    public final float z;
    NEXORotation(float x, float y, float z){
        this.x= x;
        this.y= y;
        this.z= z;
    }

    public Vec3f getVec3f(){
        return new Vec3f(x,y,z);
    }
}
