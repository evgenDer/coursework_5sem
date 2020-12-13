import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public  class Server extends Thread {

    private InetAddress address;

    public Server(Socket serverSocket) {
        address = serverSocket.getInetAddress();
        showConnection();
    }
    public void showConnection() {
        System.out.println("------------------------------");
        System.out.println(" Информация о подключении: ");
        System.out.println(" IP: " + address.toString());
        System.out.println(" Имя узла: " + address.getHostName());
        System.out.println(" Время подключения: " + new Date(System.currentTimeMillis()) + "\n");
    }

}
