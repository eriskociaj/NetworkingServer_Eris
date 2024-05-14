import java.net.*;
import java.io.*;

public class NetworkingServer {

    public static void main(String [] args) {
        // Välj ett lämpligt portnummer
        int portnumber = 8080;

        ServerSocket server = null;
        Socket client = null;

        try {
            // Skapa en serversocket
            server = new ServerSocket(portnumber);
            System.out.println("ServerSocket is created on port " + portnumber);

            // Vänta på data från klienten och svara
            while(true) {
                System.out.println("Waiting for connect request..."); 
                client = server.accept();
                System.out.println("Connect request is accepted..."); 
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

                // Läs data från klienten
                InputStream clientIn = client.getInputStream(); 
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine(); 
                System.out.println("Message received from client = " + msgFromClient);

                // Skicka svar till klienten
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true); 
                    String ansMsg = "Hello, " + msgFromClient;
                    pw.println(ansMsg);
                }

                // Stäng buffrar och strömmar
                br.close();
                client.close();
                
                // Avsluta loopen om meddelandet är "bye"
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException ie) {
            // Skriv ut felmeddelande om något går fel
            System.out.println("An error occurred: " + ie.getMessage());
        } finally {
            // Stäng serversocketen när den inte längre behövs
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred while closing the server socket: " + e.getMessage());
            }
        }
    }
}
