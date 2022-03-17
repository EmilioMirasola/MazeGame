package threads;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WriteThread extends Thread {
    private Socket connectionSocket;

    public WriteThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        while (true) {
            try {
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

                String send = inFromUser.readLine();

                outToClient.writeBytes(send + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}





