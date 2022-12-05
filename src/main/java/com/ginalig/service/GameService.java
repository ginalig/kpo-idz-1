package com.ginalig.service;

import com.ginalig.model.*;

import java.io.*;
import java.util.Scanner;

public class GameService implements IGameService {
    private IPlayer player1;
    private IPlayer player2;
    private int player1Score;
    private int player2Score;
    private static int currentRecord;

    private final Field field;
    public static GameService configureGame() throws IOException {
        currentRecord = loadRecord();
        System.out.println("Текущий рекорд: " + currentRecord);
        System.out.println("ДОСТУПНЫЕ РЕЖИМЫ: ");
        System.out.println("1) PvP");
        System.out.println("2) Легкий");
        System.out.println("3) Сложный");
        int userInput;
        while(true) {
            try {
                System.out.println("Выберите режим игры: ");
                Scanner input = new Scanner(System.in);
                userInput = input.nextInt();
                if (userInput > 3 || userInput < 1) {
                    System.out.println("Неверный номер режима");
                    continue;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Неверный ввод");
            }
        }
        var gameMode = GameMode.values()[userInput - 1];
        return new GameService(gameMode);
    }
    public GameService(IPlayer player1, IPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Score = 0;
        this.player2Score = 0;
        field = new Field();
    }
    public GameService(GameMode gameMode) {
        switch (gameMode) {
            case PlayerVsPlayer -> {
                this.player1 = new Player(ChipType.Black);
                this.player2 = new Player(ChipType.White);
            }
            case PlayerVsEasyMode -> {
                this.player1 = new Player(ChipType.Black);
                this.player2 = new Easymode(ChipType.White);
            }
            case PlayerVsHardMode -> {
                this.player1 = new Player(ChipType.Black);
                this.player2 = new Hardmode(ChipType.White);
            }
        }
        this.player1Score = 0;
        this.player2Score = 0;
        field = new Field();
    }
    @Override
    public void start() throws CloneNotSupportedException, IOException {
        while (field.availabilityCheckingStackSize() < 2) {
            printScore();
            player1.makeMove(field);
            player1Score = getPlayer1Score();
            player2Score = getPlayer2Score();
            player2.makeMove(field);
            player1Score = getPlayer1Score();
            player2Score = getPlayer2Score();
        }
        printScore();
        System.out.println("Игра окончена");
        saveRecord(player1Score > player2Score ? player1Score : player2Score);
    }

    @Override
    public int getPlayer1Score() {
        return getPlayerScore(player1);
    }

    @Override
    public int getPlayer2Score() {
        return getPlayerScore(player2);
    }

    private int getPlayerScore(IPlayer player) {
        int sum = 0;
        var field = this.field.getField();
        for (ChipType[] chipTypes : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (chipTypes[j] == player.getChipType()) {
                    sum++;
                }
            }
        }
        return sum;
    }
    private void printScore() {
        System.out.println("СЧЕТ Черные " + player1Score + " : Белые " + player2Score);
    }
    private static int loadRecord() throws IOException {
        Reader reader = new FileReader("record.txt");
        Scanner scanner = new Scanner(reader);
        int record = scanner.nextInt();
        scanner.close();
        reader.close();
        return record;
    }
    public static void saveRecord(int record) throws IOException {
        if (record > currentRecord) {
            currentRecord = record;
        }
        Writer writer = new FileWriter("record.txt");
        writer.write(currentRecord + "");
        writer.close();
    }
}
