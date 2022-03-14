package client;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPClientPlusServer {

    public static void main(String[] args) throws Exception {

        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept();

        ReadThread rt = new ReadThread(connectionSocket);
        WriteThread wt = new WriteThread(connectionSocket);

        rt.start();
        wt.start();


    }


}
