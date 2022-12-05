package com.ginalig.model;

import java.util.Stack;

public class Field implements Cloneable{
    // ' ' - empty
    // 'B' - player 1
    // 'W' - player 2
    // 'P' - possible move

    private ChipType[][] field;
    private Stack<Integer> availabilityCheckingStack;

    public Field() {
        this.field = new ChipType[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                this.field[i][j] = ChipType.Empty;
        this.field[3][3] = this.field[4][4] = ChipType.White;
        this.field[3][4] = this.field[4][3] = ChipType.Black;
        availabilityCheckingStack = new Stack<>();
    }


    public void setCell(Point point, ChipType chipType) {
        this.field[point.x()][point.y()] = chipType;
        int row = point.x(), column = point.y();
        boolean flag = false;
        var reversedChip = getReversedChipType(chipType);
        for (int i = row - 1; i >= 0; i--) {
            if (field[i][column] == chipType) {
                for (int k = row - 1; k > i; k--) {
                    flag = true;
                    if (field[k][column] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = row - 1; k > i; k--) {
                        field[k][column] = chipType;
                    }
                }
                break;
            }
        }
        flag = false;
        for (int i = row + 1; i < 8; i++) {
            if (field[i][column] == chipType) {
                for (int k = row + 1; k < i; k++) {
                    flag = true;
                    if (field[k][column] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = row + 1; k < i; k++) {
                        field[k][column] = chipType;
                    }
                }
                break;
            }
        }
        flag = false;
        for (int i = column - 1; i >= 0; i--) {
            if (field[row][i] == chipType) {
                for (int k = column - 1; k > i; k--) {
                    flag = true;
                    if (field[row][k] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = column - 1; k > i; k--) {
                        field[row][k] = chipType;
                    }
                }
                break;
            }
        }
        flag = false;
        for (int i = column + 1; i < 8; i++) {
            if (field[row][i] == chipType) {
                for (int k = column + 1; k < i; k++) {
                    flag = true;
                    if (field[row][k] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = column + 1; k < i; k++) {
                        field[row][k] = chipType;
                    }
                }
                break;
            }
        }
        flag = false;
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--) {
            if (field[i][j] == chipType) {
                for (int k = row - 1, l = column - 1; k > i && l > j; k--, l--) {
                    flag = true;
                    if (field[k][l] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = row - 1, l = column - 1; k > i && l > j; k--, l--) {
                        field[k][l] = chipType;
                    }

                }
                break;
            }
        }
        flag = false;
        for (int i = row + 1, j = column + 1; i < 8 && j < 8; i++, j++) {
            if (field[i][j] == chipType) {
                for (int k = row + 1, l = column + 1; k < i && l < j; k++, l++) {
                    flag = true;
                    if (field[k][l] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = row + 1, l = column + 1; k < i && l < j; k++, l++) {
                        field[k][l] = chipType;
                    }
                }
                break;
            }
        }
        flag = false;
        for (int i = row - 1, j = column + 1; i >= 0 && j < 8; i--, j++) {
            if (field[i][j] == chipType) {
                for (int k = row - 1, l = column + 1; k > i && l < j; k--, l++) {
                    flag = true;
                    if (field[k][l] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = row - 1, l = column + 1; k > i && l < j; k--, l++) {
                        field[k][l] = chipType;
                    }
                }
                break;
            }
        }
        flag = false;
        for (int i = row + 1, j = column - 1; i < 8 && j >= 0; i++, j--) {
            if (field[i][j] == chipType) {
                for (int k = row + 1, l = column - 1; k < i && l > j; k++, l--) {
                    flag = true;
                    if (field[k][l] != reversedChip) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (int k = row + 1, l = column - 1; k < i && l > j; k++, l--) {
                        field[k][l] = chipType;
                    }
                }
                break;
            }
        }
    }

    public ChipType[][] getField() {
        return field;
    }

    public ChipType[][] getPossibleMoves(ChipType chipType) {
        var reversedChip = getReversedChipType(chipType);
        ChipType[][] possibleMoves = new ChipType[8][8];
        for (int i = 0; i < 8; i++)
            System.arraycopy(field[i], 0, possibleMoves[i], 0, 8);
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

                            possibleMoves[i][column] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[i][column] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[row][i] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[row][i] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[i][j] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[i][j] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[i][j] = ChipType.PossibleMove;
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
                        if (flag) possibleMoves[i][j] = ChipType.PossibleMove;
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }

    static ChipType getReversedChipType(ChipType chipType) {
        return chipType == ChipType.Black ? ChipType.White : ChipType.Black;
    }

    private static char getChar(ChipType chipType) {
        return switch (chipType) {
            case White -> 'W';
            case Black -> 'B';
            case PossibleMove -> '*';
            default -> ' ';
        };
    }

    public static void printField(ChipType[][] field) {
        System.out.print(" ");
        for (int column = 0; column < 8; column++) {
            System.out.print(" | " + column);
        }
        System.out.print(" |");
        for (int row = 0; row < 8; row++) {
            System.out.println();
            System.out.println("  ---------------------------------");
            System.out.print(row + " ");
            for (int column = 0; column < 8; column++) {
                System.out.print("| " + getChar(field[row][column]) + " ");
            }
            System.out.print("|");
        }
        System.out.println();
        System.out.println("  ---------------------------------");
    }
    public void pushAvailabilityCheckingStack() {
        availabilityCheckingStack.push(0);
    }
    public void popAvailabilityCheckingStack() {
        if (availabilityCheckingStack.size() > 0) {
            availabilityCheckingStack.pop();
        }
    }
    public int availabilityCheckingStackSize() {
        return availabilityCheckingStack.size();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        Field field = (Field) obj;
        field.field = new ChipType[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(this.field[i], 0, field.field[i], 0, 8);
        }
        field.availabilityCheckingStack = (Stack<Integer>) this.availabilityCheckingStack.clone();
        return obj;
    }
}
