package client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String message;
    private String login = "tanya1234";

    public Client(String ipAddress, String port){
        try {
            clientSocket = new Socket(ipAddress, Integer.parseInt(port));
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static client.Client instance;

    public static client.Client getInstance() {
        if (instance == null) {
            instance = new client.Client("127.0.0.2", "8000");
        }
        return instance;
    }

    public void sendMessage(String message){
        try {
            outStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object){
        try {
            outStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() {
        try {
            message = (String) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public Object readObject(){
        Object object = new Object();
        try {
            object = inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {

            e.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            clientSocket.close();
            inStream.close();
            outStream.close();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
