package mopsy.productions.nexo.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public enum PipeEndState {
    NONE,
    DISCONNECTED,
    IN,
    OUT,
    PIPE;

    public boolean isPipe(){
        return this == PIPE;
    }
    public boolean isIO(){
        return this == IN || this == OUT;
    }
    public boolean isEnd(){
        return this == IN || this == OUT || this == DISCONNECTED;
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
    public boolean isDisconnected(){
        return this == DISCONNECTED;
    }
    public static PipeEndState read(NbtCompound nbt, String key){
        if(nbt.contains(key)) {
            switch (nbt.getInt(key)){
                default -> {return PipeEndState.NONE;} //Both 0 and >3 will return NONE
                case 1 -> {return PipeEndState.DISCONNECTED;}
                case 2 -> {return PipeEndState.IN;}
                case 3 -> {return PipeEndState.OUT;}
            }
        }
        else
            return PipeEndState.NONE;
    }
    public static void write(NbtCompound nbt, PipeEndState pipeEndState, String key){
        switch (pipeEndState){
            case DISCONNECTED -> nbt.putInt(key, 1);
            case IN -> nbt.putInt(key, 2);
            case OUT -> nbt.putInt(key, 3);
        }
    }

    public static PipeEndState readPacket(PacketByteBuf buf){
        switch (buf.readInt()){
            default -> {return NONE;}
            case 1 -> {return DISCONNECTED;}
            case 2 -> {return IN;}
            case 3 -> {return OUT;}
            case 4 -> {return PIPE;}
        }
    }
    public void writeToPacket(PacketByteBuf buf){
        if(this.isNone())
            buf.writeInt(0);
        if(this.isDisconnected())
            buf.writeInt(1);
        if(this.isInput())
            buf.writeInt(2);
        if(this.isOutput())
            buf.writeInt(3);
        if(this.isPipe())
            buf.writeInt(4);
    }

    public PipeEndState cycleIfEnd(){
        switch (this){
            case DISCONNECTED -> {return OUT;}
            case OUT -> {return IN;}
            case IN -> {return DISCONNECTED;}
            default -> {return this;}
        }
    }
}