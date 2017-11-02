import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        try(Socket socket = new Socket("localhost", 53210)) {
            BufferedReader buffread = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
            DataInputStream instream = new DataInputStream(socket.getInputStream());

            System.out.println("Client connected to server.");
            System.out.println("");
            System.out.println("Client writing channel = outstream & reading channel = instream initialized.");

            while(!socket.isOutputShutdown()) {
                if(buffread.ready()) {
                    System.out.println("Client start writing in channel...");
                    //Thread.sleep(1000);
                    String clientCommand = buffread.readLine();

                    outstream.writeUTF(clientCommand);
                    outstream.flush();
                    System.out.println("Client send message " + clientCommand + " to server");
                    //Thread.sleep(1000);

                    if(clientCommand.equalsIgnoreCase("quite")) {
                        System.out.println("Client kill connection.");
                        //Thread.sleep(2000);

                        if(instream.available() != 0) {
                            System.out.println("Reading...");
                            String in = instream.readUTF();
                            System.out.println(in);
                        }
                        break;
                    }

                    System.out.println("Client sent message & start waiting for data from server...");
                    //Thread.sleep(2000);

                    if(instream.available() != 0) {
                        System.out.println("Reading...");
                        String in = instream.readUTF();
                        System.out.println(in);
                    }
                }
            }


            instream.close();
            outstream.close();
            buffread.close();

            System.out.println("Closing connections & channels on clientSide - DONE");
            Thread.interrupted();
            System.exit(0);

        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // Auto-generated for if
            e.printStackTrace();
        }
    }
}
