import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class server {

    final static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] receive = new byte[50];
            DatagramPacket packet = new DatagramPacket(receive, receive.length);
            System.out.println("listening on port " + PORT);
            while (true) {
                socket.receive(packet);
                String receivedContent = new String(packet.getData(), 0, packet.getLength());
                System.out.println(receivedContent);
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}
