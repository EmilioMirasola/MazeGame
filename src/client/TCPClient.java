package client;

import threads.ReadThread;
import threads.WriteThread;

import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        Socket connectionSocket = new Socket("localhost", 6789);

        ReadThread rt = new ReadThread(connectionSocket);
        WriteThread wt = new WriteThread(connectionSocket);

        rt.start();
        wt.start();


    }
}
