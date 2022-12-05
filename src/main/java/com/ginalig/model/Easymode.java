package com.ginalig.model;

import java.util.ArrayList;
import java.util.List;

public class Easymode implements IPlayer {
    private final ChipType chipType;

    public Easymode(ChipType chipType) {
        this.chipType = chipType;
    }

    @Override
    public void makeMove(Field field) throws CloneNotSupportedException {
        var moves = getMoves(field.getField(), chipType);
        if (moves.size() == 0) {
            field.pushAvailabilityCheckingStack();
            System.out.println("Нет доступных шагов");
            return;
        }
        var move = getBestMove(moves);

        if (move.cost() == 0) {
            field.pushAvailabilityCheckingStack();
            return;
        }
        field.popAvailabilityCheckingStack();
        field.setCell(move.point(), chipType);
    }

    @Override
    public ChipType getChipType() {
        return chipType;
    }
    protected double moveCostS(int i, int j) {
        double cost = 0;
        if (i == 7 || i == 0 || j == 0 || j == 7) cost += 2;
        else cost += 1;
        return cost;
    }
    protected double moveCostSS(int i, int j) {
        double cost = 0;
        if (i == 0 && j == 0 || i == 0 && j == 7 || i == 7 && j == 0 || i == 7 && j == 7) cost += 0.8;
        else if (i == 0 || i == 7 || j == 0 || j == 7) cost += 0.4;
        return cost;
    }
    public List<Move> getMoves(ChipType[][] field, ChipType chipType) {
        var reversedChip = Field.getReversedChipType(chipType);
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (chipType != field[row][column]) continue;
                var flag = false;
                for (int i = row - 1; i >= 0; i--) {
                    if (field[i][column] == ChipType.Empty) {
                        for (int k = row - 1; k > i; k--) {
                            flag = true;
                            if (field[k][column] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = row - 1; k > i; k--) {
                                cost += moveCostS(k, column);
                            }
                            cost += moveCostSS(i, column);
                            moves.add(new Move(new Point(i, column), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = row + 1; i < 8; i++) {
                    if (field[i][column] == ChipType.Empty) {
                        for (int k = row + 1; k < i; k++) {
                            flag = true;
                            if (field[k][column] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = row + 1; k < i; k++) {
                                cost += moveCostS(k, column);
                            }
                            cost += moveCostSS(i, column);
                            moves.add(new Move(new Point(i, column), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = column - 1; i >= 0; i--) {
                    if (field[row][i] == ChipType.Empty) {
                        for (int k = column - 1; k > i; k--) {
                            flag = true;
                            if (field[row][k] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = column - 1; k > i; k--) {
                                cost += moveCostS(row, k);
                            }
                            cost += moveCostSS(row, i);
                            moves.add(new Move(new Point(row, i), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = column + 1; i < 8; i++) {
                    if (field[row][i] == ChipType.Empty) {
                        for (int k = column + 1; k < i; k++) {
                            flag = true;
                            if (field[row][k] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = column + 1; k < i; k++) {
                                cost += moveCostS(row, k);
                            }
                            cost += moveCostSS(row, i);
                            moves.add(new Move(new Point(row, i), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--) {
                    if (field[i][j] == ChipType.Empty) {
                        for (int k = row - 1, l = column - 1; k > i && l > j; k--, l--) {
                            flag = true;
                            if (field[k][l] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = row - 1, l = column - 1; k > i && l > j; k--, l--) {
                                cost += moveCostS(k, l);
                            }
                            cost += moveCostSS(i, j);
                            moves.add(new Move(new Point(i, j), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = row + 1, j = column + 1; i < 8 && j < 8; i++, j++) {
                    if (field[i][j] == ChipType.Empty) {
                        for (int k = row + 1, l = column + 1; k < i && l < j; k++, l++) {
                            flag = true;
                            if (field[k][l] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = row + 1, l = column + 1; k < i && l < j; k++, l++) {
                                cost += moveCostS(k, l);
                            }
                            cost += moveCostSS(i, j);
                            moves.add(new Move(new Point(i, j), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = row - 1, j = column + 1; i >= 0 && j < 8; i--, j++) {
                    if (field[i][j] == ChipType.Empty) {
                        for (int k = row - 1, l = column + 1; k > i && l < j; k--, l++) {
                            flag = true;
                            if (field[k][l] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = row - 1, l = column + 1; k > i && l < j; k--, l++) {
                                cost += moveCostS(k, l);
                            }
                            cost += moveCostSS(i, j);
                            moves.add(new Move(new Point(i, j), cost));
                        }
                        break;
                    }
                }
                flag = false;
                for (int i = row + 1, j = column - 1; i < 8 && j >= 0; i++, j--) {
                    if (field[i][j] == ChipType.Empty) {
                        for (int k = row + 1, l = column - 1; k < i && l > j; k++, l--) {
                            flag = true;
                            if (field[k][l] != reversedChip) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            double cost = 0;
                            for (int k = row + 1, l = column - 1; k < i && l > j; k++, l--) {
                                cost += moveCostS(k, l);
                            }
                            cost += moveCostSS(i, j);
                            moves.add(new Move(new Point(i, j), cost));
                        }
                        break;
                    }
                }
            }
        }
        return moves;
    }
    protected Move getBestMove(List<Move> moves) {
        var move = moves.get(0);
        for (var m : moves)
            if (m.cost() > move.cost())
                move = m;
        return move;
    }
}

