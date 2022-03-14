package client;

import game2022.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private Socket connectionSocket;

    public ReadThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        while (true) {
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String clientSentence = inFromClient.readLine();

                if (clientSentence.split(" ")[0].equals("opret")) {
                }

                System.out.println(clientSentence);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


