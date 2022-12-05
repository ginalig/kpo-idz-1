package com.ginalig.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements IPlayer {

    private final ChipType chipType;

    public Player(ChipType chipType) {
        this.chipType = chipType;
    }

    private void printPoints(List<Point> points) {
        int cnt = 1;
        for (Point point : points) {
            System.out.println(cnt + ") " + point.toString());
            cnt++;
        }
    }
    private List<Point> getAvailablePoints(ChipType[][] field) {
        var availablePoints = new ArrayList<Point>();
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[i].length; j++)
                if (field[i][j] == ChipType.PossibleMove)
                    availablePoints.add(new Point(i, j));
        return availablePoints;
    }
    @Override
    public void makeMove(Field field) {
        var possibleMoves = field.getPossibleMoves(chipType);
        Field.printField(possibleMoves);
        var availablePoints = getAvailablePoints(possibleMoves);
        if (availablePoints.size() == 0) {
            field.pushAvailabilityCheckingStack();
            System.out.println("Нет доступных шагов");
            return;
        }
        field.popAvailabilityCheckingStack();
        printPoints(availablePoints);
        System.out.println("ХОД " + (chipType == ChipType.Black ? "ЧЕРНЫХ" : "БЕЛЫХ"));
        int userInput;
        while(true) {
            try {
                System.out.println("Введите номер хода: ");
                Scanner input = new Scanner(System.in);
                userInput = input.nextInt();
                if (userInput > availablePoints.size() || userInput < 1) {
                    System.out.println("Неверный номер хода");
                    continue;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Неверный ввод");
            }
        }
        var point = availablePoints.get(userInput - 1);
        field.setCell(point, chipType);
    }


    @Override
    public ChipType getChipType() {
        return chipType;
    }
}
