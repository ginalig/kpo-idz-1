package com.ginalig;

import com.ginalig.service.GameService;
import com.ginalig.service.IGameService;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        while (true) {
            try {
                IGameService gameService = GameService.configureGame();
                gameService.start();
                break;
            } catch (Exception e) {
                System.out.println("ТАК ДЕЛАТЬ НЕЛЬЗЯ, ПЕРЕЗАПУСК...");
            }
        }

    }
}