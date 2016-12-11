import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketProcess extends Thread {
    private static final String API_KEY =   "specpromintebald";
    private int port;

    private ServerSocket socket;
    private Socket client;

    private BufferedReader in;
    private PrintWriter out;

    private boolean connectionAccepted = false;
    private CursorHandler cursorHandler;

    public SocketProcess(int port) {
        this.port = port;
    }

    public void init() {
        try {
//        socket = new ServerSocket();
//        socket.setReuseAddress(true);
//        socket.bind(new InetSocketAddress(port));
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cursorHandler = new CursorHandler();
    }

    @Override
    public void run() {
        try {
            while(!connectionAccepted && !Thread.currentThread().isInterrupted()) {
                client = socket.accept();
                connectionProtocol();
            }
            while(connectionAccepted && !Thread.currentThread().isInterrupted()) {
                try {
                    String[] moveQuants = in.readLine().split(",");
                    float moveX = Float.parseFloat(moveQuants[0]);
                    float moveY = Float.parseFloat(moveQuants[1]);
                    cursorHandler.move((int)moveX, (int)moveY);
                } catch(NullPointerException e) {
                    System.err.println("Improper usage of socket communication; breaking connection");
                    e.printStackTrace();
                    out.println("Improper usage of socket communication; breaking connection");
                    client.close();
                    connectionAccepted = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectionProtocol() {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("Connection established; provide API key");
            String proposedKey = in.readLine();
            if(API_KEY.equals(proposedKey)) {
                out.println("Connection accepted");
                connectionAccepted = true;
            } else {
                out.println("Connection refused; provide proper API key");
                connectionAccepted = false;
                client.close();
            }
        } catch(IOException e) {
            System.err.println("Could not establish I/O for socket communication");
            e.printStackTrace();
        }
    }
}
