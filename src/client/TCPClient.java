package client;

import model.Command;
import threads.ReadThread;

import java.net.Socket;

public class TCPClient {
    private static Socket connectionSocket;

    private static ReadThread rt;


    public static void start() throws Exception {
        connectionSocket = new Socket("localhost", 6789);
        rt = new ReadThread(connectionSocket);

        rt.start();




    }

    public static void handleRequest(Command command, String req) {

    }
}
