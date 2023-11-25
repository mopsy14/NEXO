package mopsy.productions.nexo.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public enum PipeEndState {
    NONE,
    IN,
    OUT,
    PIPE;

    public boolean isPipe(){
        return this == PIPE;
    }
    public boolean isEnd(){
        return this == IN || this == OUT;
    }
    public boolean isNone(){
        return this == NONE;
    }
    public boolean isInput(){
        return this == IN;
    }
    public boolean isOutput(){
        return this == OUT;
    }
    public static PipeEndState read(NbtCompound nbt, String key){
        if(nbt.contains(key)) {
            if (nbt.getBoolean(key))
                return PipeEndState.IN;
            else
                return PipeEndState.OUT;
        }
        else
            return PipeEndState.NONE;
    }
    public static PipeEndState readPacket(PacketByteBuf buf){
        switch (buf.readInt()){
            case 0 -> {return NONE;}
            case 1 -> {return IN;}
            case 2 -> {return OUT;}
            case 3 -> {return PIPE;}
        }
        return NONE;
    }
    public void writeToPacket(PacketByteBuf buf){
        if(this.isNone())
            buf.writeInt(0);
        if(this.isInput())
            buf.writeInt(1);
        if(this.isOutput())
            buf.writeInt(2);
        if(this.isPipe())
            buf.writeInt(3);
    }
    public static void write(NbtCompound nbt, PipeEndState pipeEndState, String key){
        switch (pipeEndState){
            case IN -> nbt.putBoolean(key, true);
            case OUT -> nbt.putBoolean(key, false);
        }
    }
}
