package com.board;

import com.board.config.FlywayConfig;
import com.board.menu.MainMenu;

public class App {
    public static void main(String[] args) {
        FlywayConfig.migrate();
        MainMenu mainMenu = new MainMenu();
        mainMenu.start();
    }
}