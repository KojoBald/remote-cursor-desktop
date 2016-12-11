import java.io.IOException;

public class Main {
    private static final String DEFAULT_HOST = "kojo-work-mac.dhcp.bsu.edu";
    private static final int DEFAULT_PORT = 3000;

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch(ArrayIndexOutOfBoundsException e) {
            port = DEFAULT_PORT;
        }

        System.out.println("listening on port " + port);

        SocketProcess socketProcess = new SocketProcess(port);
        socketProcess.init();
        socketProcess.start();

    }
}
