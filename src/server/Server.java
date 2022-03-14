package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket welcomSocket = new ServerSocket(6789);
        while (true){
            Socket connectionSocket = welcomSocket.accept();
            (new ServerThread(connectionSocket)).start();
        }
    }
}
