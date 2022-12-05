package com.ginalig.model;

public interface IPlayer {
    void makeMove(Field field) throws CloneNotSupportedException;
    ChipType getChipType();
}
