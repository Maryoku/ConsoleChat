import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        try (ServerSocket server = new ServerSocket(53210)) {
            System.out.println("Waiting for connection...");
            Socket client = server.accept();
            System.out.println("Connection accepted.");
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream created.");
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created.");

            while(!client.isClosed()) {
                System.out.println("Server reading from channel.");
                String entry = in.readUTF();
                System.out.println("READ from client message - " + entry);
                System.out.println("Server try writing to channel.");

                if (entry.equalsIgnoreCase("quite")) {
                    System.out.println("Client initialize connections suicide...");
                    out.writeUTF("Server reply - " + entry + " - OK.");
                    //Thread.sleep(3000);
                    break;
                }

                out.writeUTF("Server reply - " + entry + " - OK.");
                System.out.println("Server received message to client.");
                out.flush();
            }

            System.out.println("Client disconnected.");
            System.out.println("Closing connections & channels.");


            in.close();
            out.close();

            client.close();
            //server.close();

            System.out.println("Closing connections & channels - DONE");
            Thread.interrupted();
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
