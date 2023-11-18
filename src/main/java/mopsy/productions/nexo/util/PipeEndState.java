package mopsy.productions.nexo.util;

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
}
