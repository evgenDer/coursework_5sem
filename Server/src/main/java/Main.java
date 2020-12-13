import util.EmailSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static int clientCounter = 0;

    public static void main(String[] args)  {
        ExecutorService executeIt = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(8000);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("\nServer socket created, command console reader for listen to server commands");
            while (!server.isClosed()) {
                if (br.ready()) {
                    System.out.println("Main Server found any messages in channel, let's look at them.");
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }
                Socket client = server.accept();
                clientCounter++;
                executeIt.execute(new ThreadClientHandler(client));
                System.out.print("Connection accepted.\n");
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

