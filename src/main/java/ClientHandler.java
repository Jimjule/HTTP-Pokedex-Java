import java.io.*;
import java.net.Socket;
import HTTPServer.*;
import routes.RouteMatcher;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private InputStream in;
    private ByteArrayOutputStream out;
    private String request;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            in = clientSocket.getInputStream();
            out = new ByteArrayOutputStream();
            request = getRequest();

            String parameters = RequestReader.getRequestParams(request);
            String parametersMethod = RequestReader.getRequestMethod(parameters);
            String parametersPath = RequestReader.getRequestAddress(parameters);

            String body = RequestReader.getBody(request);

            Response response = new Response();
            Route route = RouteMatcher.getRoute(parametersPath);

            ResponseHelper.responseHandler(parametersMethod, parametersPath, body, response, route);

            out.write(response.printHeaders());
            out.write(response.printBody());
            out.write(response.printFile());

            out.writeTo(clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRequest() throws IOException {
        int readIn;
        StringBuilder input = new StringBuilder();
        while ((readIn = in.read()) != -1 && in.available() != 0) {
            input.append((char) readIn);
        }
        input.append((char) readIn);

        return input.toString();
    }
}

