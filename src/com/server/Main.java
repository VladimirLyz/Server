package com.server;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Server server;
    public static void main(String[] args) {
        try {
            server  = new Server(8989);
            server.start();
            Scanner scanner = new Scanner(System.in);
            while(true)
            {
                if(scanner.next().equals("stop")) {
                    server.stop();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
