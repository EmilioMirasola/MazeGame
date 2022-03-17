package client;

import model.Command;
import model.ResponseStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerRequest {
    private final Socket connectionSocket;

    public ServerRequest() throws IOException {
        this.connectionSocket = new Socket("localhost", 6789);
    }

    public void connect(String playerName){
        Command command = Command.CONNECT;

        performRequest(command + " " + playerName);

    }
    private void performRequest(String request) {

            try {
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeBytes(request + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}