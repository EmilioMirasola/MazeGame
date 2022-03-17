package client;

import model.ResponseStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerRequest {
    private final Socket connectionSocket;

    public ServerRequest() throws IOException {
        this.connectionSocket = new Socket("localhost", 6789);
    }

    public ResponseStatus connect(String playerName){
        return null;

    }
    public void performRequest(String data) {

            try {
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeBytes(data + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}