import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 5001;
        ServerSocket serverSocket = new ServerSocket(port);
        HTTPServer httpServer = new HTTPServer(serverSocket);
        httpServer.start();
    }
}
