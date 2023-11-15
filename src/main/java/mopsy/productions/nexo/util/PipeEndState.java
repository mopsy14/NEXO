package mopsy.productions.nexo.util;

public enum PipeEndState {
    NONE,
    IN,
    OUT,
    IN_OUT,
    PIPE;

    public boolean isPipe(){
        return this == PIPE;
    }
    public boolean isEnd(){
        return this == IN || this == OUT || this == IN_OUT;
    }
    public boolean isNone(){
        return this == NONE;
    }
}
