package com.ginalig.service;

import java.io.IOException;

public interface IGameService {
    void start() throws Exception;

    int getPlayer1Score();
    int getPlayer2Score();
}
