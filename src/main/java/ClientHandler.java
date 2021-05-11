import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

            String parameters = RequestReader.requestHandler(request);
            String parametersMethod = RequestReader.findRequestMethod(parameters);
            String parametersPath = RequestReader.findRequestAddress(parameters);

            String body = RequestReader.getBody(request);

            Response response = new Response();

            ResponseBuilder.responseHandler(parametersMethod, parametersPath, body, response);

            if (parametersPath.equals("/health-check.html")) {
                out.write((response.getParams() + response.getHeaders() + "\r\n" + new String(response.getFile())).getBytes(StandardCharsets.UTF_8));
            } else if (parametersPath.equals("/doggo.png")) {
                out.write((response.getParams() + response.getHeaders() + "\r\n").getBytes(StandardCharsets.UTF_8));
                out.write(response.getFile());

            } else if (parametersPath.equals("/kitteh.jpg")) {
                out.write((response.getParams() + response.getHeaders() + "\r\n").getBytes(StandardCharsets.UTF_8));
                out.write(response.getFile());
            } else if (parametersPath.equals("/kisses.gif")) {
                out.write((response.getParams() + response.getHeaders() + "\r\n").getBytes(StandardCharsets.UTF_8));
                out.write(response.getFile());
            } else {
                out.write(response.print().getBytes(StandardCharsets.UTF_8));
            }
            out.writeTo(clientSocket.getOutputStream());
            out.flush();
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

