package com.ginalig.model;

import java.util.ArrayList;
import java.util.List;

public class Hardmode extends Easymode{
    public Hardmode(ChipType chipType) {
        super(chipType);
    }

    @Override
    public void makeMove(Field field) throws CloneNotSupportedException {
        var moves = getMoves(field.getField(), getChipType());
        List<Field> tempFields = new ArrayList<>();
        if (moves.size() == 0) {
            field.pushAvailabilityCheckingStack();
            System.out.println("Нет доступных шагов");
            return;
        }

        for (Move value : moves) {
            var tempField = (Field) field.clone();
            tempField.popAvailabilityCheckingStack();
            tempField.setCell(value.point(), getChipType());
            tempFields.add(tempField);
        }
        for (int i = 0; i < tempFields.size(); i++) {
            if (getMoves(tempFields.get(i).getField(), getChipType()).size() == 0) {
                continue;
            }
            moves.set(i, new Move(moves.get(i).point(),
                    getBestMove(getMoves(tempFields.get(i).getField(), Field.getReversedChipType(getChipType()))).cost() - moves.get(i).cost()));
        }
        // max cost
        var move = getBestMove(moves);

        field.popAvailabilityCheckingStack();
        field.setCell(move.point(), getChipType());
    }
}
